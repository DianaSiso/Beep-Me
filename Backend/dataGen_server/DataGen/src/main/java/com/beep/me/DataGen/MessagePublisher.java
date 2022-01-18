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

    @Scheduled(fixedRate = 30000)
    @GetMapping("/publish")
    public String publishOrder() {

        if (range.getProbability(LocalDateTime.now().getHour())){

            System.out.println(rest);
        
            DataGen dataGen = new DataGen();

            Order toSendOrder  = dataGen.getOrder();

            template.convertAndSend(MQConfig.EXCHANGE, MQConfig.ROUTING_KEY, toSendOrder);

            return "Order Published!";
        }
        return "Order not published";

    }
    
}
