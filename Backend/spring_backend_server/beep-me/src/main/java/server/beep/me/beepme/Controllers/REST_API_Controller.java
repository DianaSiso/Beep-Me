package server.beep.me.beepme.Controllers;

import java.util.ArrayList;
import java.util.HashMap;

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
import server.beep.me.beepme.Forms.DelayedForm;
import server.beep.me.beepme.Forms.OrderForm;
import server.beep.me.beepme.Forms.RestForm;
import server.beep.me.beepme.Forms.StateForm;
import server.beep.me.beepme.Services.BusinessLogic;

// @CrossOrigin(origins = "http://deti-engsoft-02.ua.pt:8080")
@RestController
public class REST_API_Controller {

   @Autowired
   private BusinessLogic backend;

   Restaurant rest;

     
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> login(@RequestParam(name = "username") String username, @RequestParam(name = "pwd") String password) {
        boolean response = backend.verifyUser(username, password);

        if (response) {
            return ResponseEntity.ok("Login was succssefull!");
        } else {
            return new ResponseEntity<>("Credentials were wrong!",HttpStatus.FORBIDDEN);
        }
    }

     
    @RequestMapping(value = "/orders/restaurant", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ArrayList<Order> orders_by_restaurant(@RequestParam(name = "rest_id") String rest_id) {
        Integer id = Integer.parseInt(rest_id);
        ArrayList<Order> orders = backend.getOrdersByRestID(id);

        if (orders == null) {
            return new ArrayList<Order>();
        } else {
            return orders;
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

     
    @RequestMapping(value = "/orders/meanTime/restaurant", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Double orders_mean_time_by_restaurant(@RequestParam(name = "rest_id") Integer rest_id) {

        Double meanTime = backend.getMeanDeliveryTimeByRestaurant(rest_id);
        return meanTime;

    }

     
    @RequestMapping(value = "/orders/meanTime/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Double> orders_mean_time() {

        HashMap<String, Double> meanTimeMap = backend.getMeanDeliveryTimeToAllRestaurants();
        return meanTimeMap;

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
    

    
}
