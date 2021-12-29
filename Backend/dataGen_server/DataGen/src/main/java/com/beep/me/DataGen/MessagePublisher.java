package com.beep.me.DataGen;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beep.me.DataGen.Order;

@RestController
@EnableScheduling
public class MessagePublisher {

    @Autowired
    private RabbitTemplate template;

    @Scheduled(fixedRate = 1000)
    @GetMapping("/publish")
    public String publishOrder() {
        
        DataGen dataGen = new DataGen();

        Order toSendOrder  = dataGen.getOrder();

        // order.setCode(toSendOrder.getCode());
        // order.setOrdered(toSendOrder.getOrdered());
        // order.setPrevisted(toSendOrder.getPrevisted());
        // order.setRestaurant(toSendOrder.getRestaurant());

        template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, toSendOrder);

        return "Order Published!";

    }
    
}
