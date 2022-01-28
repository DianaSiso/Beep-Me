package com.beep.me.DataGen;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;
    
    @Value("${rest}")
    private String rest;

    private static Range range = new Range();

    @Scheduled(fixedRate = 5000)
    @GetMapping("/publish")
    public String publishOrder() {
        boolean send = range.getProbability(LocalDateTime.now().getHour());
        System.out.println("SENDING: " + send);
        if (send){
        
            DataGen dataGen = new DataGen();

            Order toSendOrder  = dataGen.getOrder();
            System.out.println(toSendOrder.getRestaurant());
            template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, toSendOrder);

            System.out.print("Order Published!");
            return "Order Published!";
        }
        System.out.print("Order not Published!");
        return "Order not published";

    }

    @Scheduled(fixedRate = 5000)
    @GetMapping("/publishOldOrders")
    public void publishOldOrder() {
        DataGen dataGen = new DataGen();

        Order toSendOrder  = dataGen.getOldOrder();
        System.out.println(toSendOrder.getRestaurant());
        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY_OLD_ORDERS, toSendOrder);

    }
    
}
