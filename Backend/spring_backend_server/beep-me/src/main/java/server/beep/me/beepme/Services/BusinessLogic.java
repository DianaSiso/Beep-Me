package server.beep.me.beepme.Services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import server.beep.me.beepme.Entities.Order;
import server.beep.me.beepme.Entities.Restaurant;
import server.beep.me.beepme.Entities.State;
import server.beep.me.beepme.Entities.User;
import server.beep.me.beepme.Forms.OrderForm;
import server.beep.me.beepme.Forms.RestForm;
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

    public Order create_order(OrderForm order) {
        LocalDateTime orderedTime = LocalDateTime.parse(order.getOrderedTime());
        LocalDateTime possibleDeliveryTime = LocalDateTime.parse(order.getPossibleDeliveryTime());
        State state = State.valueOf(order.getState());
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
    
