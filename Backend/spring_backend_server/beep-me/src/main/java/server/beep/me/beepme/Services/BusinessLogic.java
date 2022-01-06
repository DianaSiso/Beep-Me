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
import server.beep.me.beepme.Forms.OrderForm;
import server.beep.me.beepme.Forms.RestForm;
import server.beep.me.beepme.Forms.StateForm;
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

    public ArrayList<Order> getOrdersByRestID(Integer id) {
        Optional<Restaurant> rest_opt = restRepository.findById(id);

        if (rest_opt.isPresent()){
            Restaurant rest = rest_opt.get();
            List<Order> orders = ordersRepository.findByRestaurant(rest, Sort.unsorted());

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

    public Double getMeanDeliveryTimeByRestaurant(Integer id) {
        Optional<Restaurant> rest_opt = restRepository.findById(id);

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
            return meanMinutes;
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
            double meanMinutes = sum / count;
            rest_to_meanTime.put(rest.getName(), meanMinutes);

        }

        return rest_to_meanTime;

    }

    public boolean change_order_state(StateForm s) {
        State state = null;
        System.out.println(s.getState());
        for (State st : State.values()) {
            System.out.println(st.toString());
            if(st.toString().equals(s.getState())) {
                System.out.println("IS EQUAL");
                state = st;
                break;
            }
        }

        if (state == null) {
            System.out.println("STATE IS NULL");
            return false;
        }

        Optional<Order> possibleOrder = ordersRepository.findById(s.getOrder_id());
        System.out.println(possibleOrder.isPresent());
        if (possibleOrder.isPresent()) {
            System.out.println("HEREE");
            Order order = possibleOrder.get();
            order.setState(state);
            Order savedOrder = ordersRepository.save(order);

            if (savedOrder != null) {
                return true;
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
                                        state);
            Order saved_order = ordersRepository.save(to_save);

            return saved_order;
        }

        return null;
        
    }

    public Restaurant create_rest(RestForm rest) {
        String name = rest.getName();
        List<Restaurant> rests = restRepository.findByName(name);
        if (rests.isEmpty()) {
            Restaurant to_save = new Restaurant(name);
            Restaurant saved_rest = restRepository.save(to_save);
    
            return saved_rest;
        }

        return null;
        
    }

    public Order saveOrder(OrderMessage orderMessage) {
        List<Restaurant> rest = restRepository.findByName(orderMessage.getRestaurant());
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
}
    
