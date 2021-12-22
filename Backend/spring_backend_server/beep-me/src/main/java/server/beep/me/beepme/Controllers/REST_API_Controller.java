package server.beep.me.beepme.Controllers;

import java.util.ArrayList;

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
import server.beep.me.beepme.Forms.OrderForm;
import server.beep.me.beepme.Forms.RestForm;
import server.beep.me.beepme.Services.BusinessLogic;

@RestController
public class REST_API_Controller {

   @Autowired
   private BusinessLogic backend;

   Restaurant rest;

    // @RequestMapping("/")
    // public String home() {
    //     return "Hello Docker World";
    // }

    @CrossOrigin(origins = "http://deti-engsoft-02.ua.pt:8080")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> login(@RequestParam(name = "username") String username, @RequestParam(name = "pwd") String password) {
        boolean response = backend.verifyUser(username, password);

        if (response) {
            return ResponseEntity.ok("Login was succssefull!");
        } else {
            return new ResponseEntity<>("Credentials were wrong!",HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin(origins = "http://deti-engsoft-02.ua.pt:8080")
    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ArrayList<Order> orders(@RequestParam(name = "rest_id") String rest_id) {
        Integer id = Integer.parseInt(rest_id);
        ArrayList<Order> orders = backend.getOrdersByRestID(id);

        if (orders == null) {
            return new ArrayList<Order>();
        } else {
            return orders;
        }
    }

    @CrossOrigin(origins = "http://deti-engsoft-02.ua.pt:8080")
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

    @CrossOrigin(origins = "http://deti-engsoft-02.ua.pt:8080")
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
