package server.beep.me.beepme.Controllers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import server.beep.me.beepme.Entities.Order;
import server.beep.me.beepme.Entities.Restaurant;
import server.beep.me.beepme.Entities.State;
import server.beep.me.beepme.Entities.User;
import server.beep.me.beepme.Forms.DelayedForm;
import server.beep.me.beepme.Forms.LoginForm;
import server.beep.me.beepme.Forms.LoginResponseForm;
import server.beep.me.beepme.Forms.OrderForm;
import server.beep.me.beepme.Forms.OrderMobile;
import server.beep.me.beepme.Forms.RestForm;
import server.beep.me.beepme.Forms.StateForm;
import server.beep.me.beepme.Forms.UserForm;
import server.beep.me.beepme.Services.BusinessLogic;

@CrossOrigin
@RestController
public class Beep_Me_Endpoints {

   @Autowired
   private BusinessLogic backend;

    private void getUserFromSession(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Object att = request.getSession().getAttribute("USER_ID");

        if (att != null) {
            System.out.println(att);
        } else {
            System.out.println("NOT A USER_ID");
        }
    }

     
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponseForm login(@RequestBody LoginForm loginInfo) {
        boolean response = backend.verifyUser(loginInfo.getUsername(), loginInfo.getPassword());

        if (response) {
            LoginResponseForm resp = backend.generateLoginResponse(loginInfo);
            return resp;
        } else {
            LoginResponseForm resp = new LoginResponseForm();
            resp.setStatus("FORBIDDEN");
            resp.setRest_id(-1);
            resp.setManager(-1);
            return resp;
        }
    }

    @RequestMapping(value = "/orders/code", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderMobile orders_by_code(@RequestParam(name = "code") String code) {
        
        if (code == null) {
            return new OrderMobile();
        }
        OrderMobile order = backend.getOrderByCode(code);

        if (order == null) {
            return new OrderMobile();
        } else {
            return order;
        }
    }

     
    @RequestMapping(value = "/orders/restaurant", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ArrayList<Order> orders_by_restaurant(@RequestParam(name = "rest_id") String rest_id) {
        
        if (rest_id == null) {
            return new ArrayList<Order>();
        }

        Integer id = Integer.parseInt(rest_id);
        ArrayList<Order> orders = backend.getOrdersByRestID(id);

        if (orders == null) {
            return new ArrayList<Order>();
        } else {
            return orders;
        }
    }

    @RequestMapping(value = "/orders/cancel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> cancel_order(@RequestParam(name = "order_id") String order_id) {
        boolean done = backend.cancel_order(order_id);
        
        if (done) {
            return new ResponseEntity<>("Order deleted successesfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order was not deleted!",HttpStatus.CONFLICT);
        }
    }

     
    @RequestMapping(value = "/orders/state", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ArrayList<Order> orders_by_state(@RequestParam(name = "state") String s) {
        
        ArrayList<Order> orders = backend.getOrdersByState(s);
        return orders;
    }

     
    @RequestMapping(value = "/orders/date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ArrayList<Order> orders_by_date(@RequestParam(name = "date") String date) {

        ArrayList<Order> orders = backend.getOrdersByOrderedDate(date);
        return orders;

    }
     
    @RequestMapping(value = "/orders/state", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> change_order_state(@RequestBody StateForm state) {
        boolean saved = backend.change_order_state(state);
        if (saved) {
            return new ResponseEntity<>("Order updated successesfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order was not updated!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @RequestMapping(value = "/orders/delayed", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> delayed(@RequestBody DelayedForm delayed) {
        boolean saved = backend.delay_order(delayed);
        if (saved) {
            return new ResponseEntity<>("Order delayed successesfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order was not delayed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> create_order(@RequestBody OrderForm order) {
        Order saved_order = backend.create_order(order);
        
        if (saved_order != null) {
            return new ResponseEntity<>("Order created successesfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Order was not created!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> create_user(@RequestBody UserForm user) {
        User saved_user = backend.create_user(user);
        
        if (saved_user != null) {
            return new ResponseEntity<>("User created successesfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User was not created!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     
    @RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> create_restaurant(@RequestBody RestForm rest) {
        Restaurant saved_rest = backend.create_rest(rest);
        
        if (saved_rest != null) {
            return new ResponseEntity<>("Restaurant created successesfully!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Restaurant already exists!",HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/restaurant/total_orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Integer> number_of_orders(@RequestParam(name = "rest_id", required = false) Integer rest_id) {

        if (rest_id == null) {
            HashMap<String, Integer> rests_to_n_orders_map = backend.getNumberOfOrdersToAllRestaurants();
            return rests_to_n_orders_map;
        } else {
            HashMap<String, Integer> rest_to_n_orders_map = backend.getNumberOfOrdersToSpecificRestaurant(rest_id);
            return rest_to_n_orders_map;
        }

    }


    @RequestMapping(value = "/restaurant/orders/meanTime", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Double> orders_mean_time(@RequestParam(name = "rest_id", required = false) Integer rest_id) {

        if (rest_id != null) {
            HashMap<String, Double> meanTimeMap = backend.getMeanDeliveryTimeByRestaurant(rest_id);
            return meanTimeMap;
        } else {
            HashMap<String, Double> meanTimeMap = backend.getMeanDeliveryTimeToAllRestaurants();
            return meanTimeMap;
        }

    }

    @RequestMapping(value = "/restaurant/orders/delivered", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Integer> orders_delivered(@RequestParam(name = "rest_id", required = false) Integer rest_id, @RequestParam(name = "late", required = false) String late) {

        if (rest_id != null) {
            if (late != null) {
                HashMap<String, Integer> deliveredMap = backend.getNumberOfOrdersLateDeliveriesSpecificRestaurants(rest_id);
                return deliveredMap;
            } else {
                HashMap<String, Integer> deliveredMap = backend.getNumberOfOrdersDeliveredSpecificRestaurant(rest_id);
                return deliveredMap;
            }
            
        } else {
            if (late != null) {
                HashMap<String, Integer> deliveredSpecificRestMap = backend.getNumberOfOrdersLateDeliveriesAllRestaurants();
                return deliveredSpecificRestMap;
            } else {
                HashMap<String, Integer> deliveredSpecificRestMap = backend.getNumberOfOrdersDeliveredAllRestaurants();
                return deliveredSpecificRestMap;
            }
            
        }

    }

    @RequestMapping(value = "/restaurant/orders/notDelivered", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Integer> orders_not_delivered(@RequestParam(name = "rest_id", required = false) Integer rest_id) {
        
        if (rest_id != null) {
            HashMap<String, Integer> nondeliveredMap = backend.getOrdersNonDeliveredSpecificRestaurants(rest_id);
            return nondeliveredMap;
            
        } else {
            HashMap<String, Integer> nondeliveredSpecificRestMap = backend.getOrdersNonDeliveredAllRestaurants();
            return nondeliveredSpecificRestMap;
        }
    }

    @RequestMapping(value = "/restaurant/orders/perhour", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, HashMap<Integer, Integer>> orders_per_hour() {
        
        HashMap<String, HashMap<Integer, Integer>> preHourMap = backend.getOrdersPerHour();
        return preHourMap;
    }


}
