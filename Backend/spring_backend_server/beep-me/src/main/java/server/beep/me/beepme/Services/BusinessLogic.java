package server.beep.me.beepme.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import server.beep.me.beepme.Entities.Order;
import server.beep.me.beepme.Entities.Restaurant;
import server.beep.me.beepme.Entities.State;
import server.beep.me.beepme.Entities.User;
import server.beep.me.beepme.Forms.DelayedForm;
import server.beep.me.beepme.Forms.LoginForm;
import server.beep.me.beepme.Forms.LoginResponseForm;
import server.beep.me.beepme.Forms.OrderForm;
import server.beep.me.beepme.Forms.RestForm;
import server.beep.me.beepme.Forms.StateForm;
import server.beep.me.beepme.Forms.UserForm;
import server.beep.me.beepme.MessageQueue.OrderMessage;
import server.beep.me.beepme.Respositories.OrdersRepository;
import server.beep.me.beepme.Respositories.RestaurantsRepository;
import server.beep.me.beepme.Respositories.UserRepository;

@Service
public class BusinessLogic {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantsRepository restRepository;

    @Autowired
    OrdersRepository ordersRepository;

    public boolean verifyUser(String username, String pwd) {

        ArrayList<User> users = userRepository.findUsersByUsername(username);

        for (User user: users) {
            if (user.getPassword().equals(pwd)) {
                return true;
            }
        }
        return false;
        
    }

    public LoginResponseForm generateLoginResponse(LoginForm loginInfo) {

        LoginResponseForm resp = new LoginResponseForm();
        ArrayList<User> users = userRepository.findUsersByUsername(loginInfo.getUsername());
        User user = users.get(0);

        if (user ==null) {
            resp.setRest_id(-1);
            resp.setStatus("FORBIDDEN");
            resp.setManager(-1);
            return resp;
        }

        Integer user_id = user.getId();
        List<Restaurant> rests = restRepository.findByUserID(user_id);
        Restaurant rest = rests.get(0);

        if(rest == null) {
            resp.setRest_id(-1);
            resp.setStatus("NOT OWN RESTAURANT");
            resp.setManager(-1);
            return resp;
        }

        Integer rest_id = rest.getId();

        resp.setRest_id(rest_id);
        resp.setStatus("OK");
        if (user.isManager()) {
            resp.setManager(1);
        } else {
            resp.setManager(0);
        }
        return resp;
    }

    public ArrayList<Order> getOrdersByRestID(Integer id) {
        Optional<Restaurant> rest_opt = restRepository.findById(id);

        if (rest_opt.isPresent()){
            Restaurant rest = rest_opt.get();
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            ArrayList<Order> toReturn = new ArrayList<>();
            for (Order order : orders) {
                LocalDateTime delivereDateTime = order.getPossibleDeliveryTime();
                LocalDateTime now = LocalDateTime.now();

                Duration duration = Duration.between(now, delivereDateTime);
                long diff = Math.abs(duration.toMinutes());
                System.out.println(diff);
                if (order.getState().toString().equals(State.DELIVERED.toString())) {
                    
                    if ( diff <= 10) {
                        toReturn.add(order);
                    }
                } else if (order.getState().toString().equals(State.READY.toString())) {
                    Duration durationReady = Duration.between(now, delivereDateTime);
                    durationReady.plusMinutes(30);
                    long diffReady = Math.abs(duration.toMinutes());
                    if (diffReady < 0) {
                        order.setState(State.NON_DELIVERED);
                    }
                } else {
                    if (diff < 0) {
                        delivereDateTime.plusMinutes(5);
                        order.setState(State.LATE);
                        order.setLate(true);
                        order.setPossibleDeliveryTime(delivereDateTime);
                    }
                    toReturn.add(order);
                }
            }

            return (ArrayList<Order>)orders;
        } else {
            return null;
        }
    }

    public ArrayList<Order> getOrdersByState(String s) {
        State state = null;
        for (State st : State.values()) {
            if(st.toString().equals(s))
               state = st;
               break;
        }

        if (state == null) {
            ArrayList<Order> orders = null;
            return orders;
        }

        List<Order> orders = ordersRepository.findByState(state);
        return (ArrayList<Order>)orders;
    }

    public ArrayList<Order> getOrdersByOrderedDate(String d) {
        LocalDateTime date = LocalDateTime.parse(d);
        List<Order> orders = ordersRepository.findByOrderedTime(date);
        return (ArrayList<Order>)orders;
    }

    public boolean change_order_state(StateForm s) {
        State state = null;
        System.out.println(s.getState());
        for (State st : State.values()) {
            System.out.println(st.toString());
            if(st.toString().equals(s.getState())) {
                state = st;
                break;
            }
        }

        if (state == null) {
            return false;
        }

        Optional<Order> possibleOrder = ordersRepository.findById(s.getOrder_id());
        System.out.println(possibleOrder.isPresent());
        if (possibleOrder.isPresent()) {
            Order order = possibleOrder.get();
            if (!order.getState().toString().equals(State.DELIVERED.toString())) {
                order.setState(state);
                if (state.toString().equals(State.DELIVERED.toString())) {
                    LocalDateTime now = LocalDateTime.now();
                    order.setPossibleDeliveryTime(now);
                }
                if (state.toString().equals(State.LATE.toString())) order.setLate(true);
                Order savedOrder = ordersRepository.save(order);
                if (savedOrder != null) {
                    return true;
                }
            }
        }

        return false;

    }

    public boolean delay_order(DelayedForm delayed) {

        Optional<Order> possibleOrder = ordersRepository.findById(delayed.getOrder_id());

        if (possibleOrder.isPresent()) {
            Order order = possibleOrder.get();
            LocalDateTime deliveryTime = order.getPossibleDeliveryTime();
            deliveryTime.plusMinutes(delayed.getMinutes());
            order.setPossibleDeliveryTime(deliveryTime);
            order.setState(State.LATE);
            order.setLate(true);
            Order savedOrder = ordersRepository.save(order);

            if (savedOrder != null) {
                return true;
            }

        }
        return false;
    }

    public Order create_order(OrderForm order) {
        LocalDateTime orderedTime = LocalDateTime.parse(order.getOrderedTime());
        LocalDateTime possibleDeliveryTime = LocalDateTime.parse(order.getPossibleDeliveryTime());
        State state = null;
        for (State st : State.values()) {
            if(st.toString().equals(order.getState()))
               state = st;
               break;
        }

        if (state == null) {
            return null;
        }

        Optional<Restaurant> rest = restRepository.findById(order.getRestaurant_id());

        if (rest.isPresent()) {
            Restaurant restaurant = rest.get();
            Order to_save = new Order( restaurant,
                                        orderedTime, 
                                        possibleDeliveryTime,
                                        order.getCode(),
                                        state,
                                        false);
            Order saved_order = ordersRepository.save(to_save);

            return saved_order;
        }

        return null;
        
    }

    public User create_user(UserForm userForm) {
        boolean manager;
        
        if (userForm.getManager() == null) {
            return null;
        }

        if (userForm.getManager().toLowerCase().equals("true".toLowerCase())) {
            manager = true;
        } else {
            manager = false;
        }

        ArrayList<User> users = userRepository.findUsersByUsername(userForm.getUsername());

        if (users.isEmpty()) {
            User user = new User(userForm.getUsername(), userForm.getPassword(), manager);
            User saved_user = userRepository.save(user);
            List<Restaurant> rests = restRepository.findByName(userForm.getUsername());

            if (rests.isEmpty()) {
                Restaurant to_save = new Restaurant(userForm.getUsername(), saved_user.getId());
                Restaurant saved_rest = restRepository.save(to_save);
            }

            return saved_user;
        }
        
        return null;
        
    }

    public Restaurant create_rest(RestForm rest) {
        String name = rest.getName();
        List<User> users = userRepository.findByUsername(name);

        User user = users.get(0);
        if (user == null) {
            return null;
        }
        Integer user_id = user.getId();


        List<Restaurant> rests = restRepository.findByName(name);

        if (rests.isEmpty()) {
            Restaurant to_save = new Restaurant(name, user_id);
            Restaurant saved_rest = restRepository.save(to_save);
    
            return saved_rest;
        }

        return null;
        
    }

    public Order saveOrder(OrderMessage orderMessage) {
        String rest_name = orderMessage.getRestaurant().replace(" ", "").replace("'", "").toLowerCase();
        System.out.println(rest_name);
        List<Restaurant> rest = restRepository.findByName(rest_name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
        LocalDateTime ordereDateTime = LocalDateTime.parse(orderMessage.getOrdered(), formatter);
        LocalDateTime previstedDateTime = LocalDateTime.parse(orderMessage.getPrevisted(), formatter);
        State state = State.ORDERED;

        if (rest.isEmpty()) {
            return null;
        }
        Order order = new Order();
        order.setCode(orderMessage.getCode());
        order.setRestaurant(rest.get(0));
        order.setOrderedTime(ordereDateTime);
        order.setPossibleDeliveryTime(previstedDateTime);
        order.setState(state);

        Order savedOrder = ordersRepository.save(order);
        return savedOrder;
    }

    public boolean cancel_order(String order_id) {
        try {
            ordersRepository.deleteById(Integer.parseInt(order_id));
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }

    public HashMap<String, Double> getMeanDeliveryTimeByRestaurant(Integer id) {
        Optional<Restaurant> rest_opt = restRepository.findById(id);
        HashMap<String, Double> rest_map = new HashMap<>();
        if (rest_opt.isPresent()){
            Restaurant rest = rest_opt.get();
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            long sum = 0;
            int count = 0;
            for (Order order : orders) {
                if (order.getState().equals(State.DELIVERED)) {
                    LocalDateTime orderedDate = order.getOrderedTime();
                    LocalDateTime deliveredDate = order.getPossibleDeliveryTime();

                    Duration duration = Duration.between(orderedDate, deliveredDate);
                    long seconds = duration.getSeconds();
                    long minutes = TimeUnit.SECONDS.toMinutes(seconds);
                    sum += minutes;
                    count += 1;
                    
                }
            }
            double meanMinutes = sum / count;
            rest_map.put(rest.getName(), meanMinutes);
            return rest_map;
        } else {
            return null;
        }
    }

    public HashMap<String, Double> getMeanDeliveryTimeToAllRestaurants() {
        Iterable<Restaurant> restaurants = restRepository.findAll();

        HashMap<String, Double> rest_to_meanTime = new HashMap<>();

        for (Restaurant rest : restaurants) {
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            long sum = 0;
            int count = 0;
            for (Order order : orders) {
                if (order.getState().equals(State.DELIVERED)) {
                    LocalDateTime orderedDate = order.getOrderedTime();
                    LocalDateTime deliveredDate = order.getPossibleDeliveryTime();

                    Duration duration = Duration.between(orderedDate, deliveredDate);
                    long seconds = duration.getSeconds();
                    long minutes = TimeUnit.SECONDS.toMinutes(seconds);
                    sum += minutes;
                    count += 1;
                    
                }
            }
            if (count > 0) {
                double meanMinutes = sum / count;
                rest_to_meanTime.put(rest.getName(), meanMinutes);
            }

        }

        return rest_to_meanTime;

    }

    public HashMap<String, Integer> getNumberOfOrdersToAllRestaurants() {
        Iterable<Restaurant> restaurants = restRepository.findAll();

        HashMap<String, Integer> rest_to_num_orders = new HashMap<>();

        for (Restaurant rest : restaurants) {
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            rest_to_num_orders.put(rest.getName(), orders.size());

        }

        return rest_to_num_orders;

    }

    public HashMap<String, Integer> getNumberOfOrdersToSpecificRestaurant(Integer rest_id) {

        HashMap<String, Integer> rest_to_num_orders = new HashMap<>();
        Optional<Restaurant> rest = restRepository.findById(rest_id);

        if (rest.isPresent()) {
            Restaurant restaurant = rest.get();
            List<Order> orders = ordersRepository.findByRestaurant(restaurant, Sort.unsorted());
            rest_to_num_orders.put(restaurant.getName(), orders.size());
        }
        

        return rest_to_num_orders;

    }

    public HashMap<String, Integer> getNumberOfOrdersDeliveredAllRestaurants() {

        HashMap<String, Integer> rest_to_orders_delivered = new HashMap<>();
        Iterable<Restaurant> restaurants = restRepository.findAll();

        for (Restaurant rest : restaurants) {

            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            int count = 0;
            for (Order order : orders) if (order.getState().equals(State.DELIVERED)) count ++;
            rest_to_orders_delivered.put(rest.getName(), count);

        }
        

        return rest_to_orders_delivered;

    }

    public HashMap<String, Integer> getNumberOfOrdersDeliveredSpecificRestaurant(Integer rest_id) {

        HashMap<String, Integer> rest_to_delivered = new HashMap<>();
        Optional<Restaurant> rest = restRepository.findById(rest_id);

        if (rest.isPresent()) {
            Restaurant restaurant = rest.get();
            List<Order> orders = ordersRepository.findByRestaurant(restaurant, Sort.unsorted());
            int count = 0;
            for (Order order : orders) if (order.getState().equals(State.DELIVERED)) count ++ ;
            rest_to_delivered.put(restaurant.getName(), count);
        }
        

        return rest_to_delivered;

    }

    public HashMap<String, Integer> getNumberOfOrdersLateDeliveriesAllRestaurants() {

        HashMap<String, Integer> rest_to_orders_delivered = new HashMap<>();
        Iterable<Restaurant> restaurants = restRepository.findAll();

        for (Restaurant rest : restaurants) {

            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            int count = 0;
            for (Order order : orders) if ((order.getState().equals(State.DELIVERED))&& order.getLate()) count ++;
            rest_to_orders_delivered.put(rest.getName(), count);

        }
        

        return rest_to_orders_delivered;

    }

    public HashMap<String, Integer> getNumberOfOrdersLateDeliveriesSpecificRestaurants(Integer rest_id) {

        HashMap<String, Integer> map_to_return = new HashMap<>();
        Optional<Restaurant> rest = restRepository.findById(rest_id);

        if (rest.isPresent()) {
            Restaurant restaurant = rest.get();
            List<Order> orders = ordersRepository.findByRestaurant(restaurant, Sort.unsorted());
            int count = 0;
            for (Order order : orders) if ((order.getState().equals(State.DELIVERED))&& order.getLate()) count ++;
            map_to_return.put(restaurant.getName(), count);
        }
        

        return map_to_return;

    }

    public HashMap<String, Integer> getOrdersNonDeliveredSpecificRestaurants(Integer rest_id) {

        HashMap<String, Integer> map_to_return = new HashMap<>();
        Optional<Restaurant> rest = restRepository.findById(rest_id);

        if (rest.isPresent()) {
            Restaurant restaurant = rest.get();
            List<Order> orders = ordersRepository.findByRestaurant(restaurant, Sort.unsorted());
            int count = 0;
            ArrayList<Order> list = new ArrayList<>();
            for (Order order : orders) if ((order.getState().equals(State.NON_DELIVERED))) list.add(order);
            map_to_return.put(restaurant.getName(), count);
        }
        

        return map_to_return;

    }

    public HashMap<String, Integer> getOrdersNonDeliveredAllRestaurants() {

        HashMap<String, Integer> map_to_return = new HashMap<>();
        Iterable<Restaurant> restaurants = restRepository.findAll();

        for (Restaurant rest: restaurants) {
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());
            int count = 0;
            ArrayList<Order> list = new ArrayList<>();
            for (Order order : orders) if ((order.getState().equals(State.NON_DELIVERED))) list.add(order);
            map_to_return.put(rest.getName(), count);
        }

        return map_to_return;

    }

    public HashMap<String, HashMap<Integer, Integer>> getOrdersPerHour() {

        HashMap<String, HashMap<Integer, Integer>> map_to_return = new HashMap<>();
        Iterable<Restaurant> restaurants = restRepository.findAll();

        for (Restaurant rest: restaurants) {
            HashMap<Integer, Integer> hours_map = new HashMap<>();
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

            for (Order order: orders) {
                LocalDateTime orderedDate = order.getOrderedTime();
                Integer hour = orderedDate.getHour();
                if (hours_map.get(hour) != null) {
                    Integer num = hours_map.get(hour);
                    hours_map.replace(hour, num+1);
                } else {
                    hours_map.put(hour, 1);
                }
            }
            map_to_return.put(rest.getName(), hours_map);
        }
        return map_to_return;
    }
}
    
