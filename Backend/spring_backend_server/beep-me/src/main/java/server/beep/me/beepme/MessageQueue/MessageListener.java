package server.beep.me.beepme.MessageQueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import server.beep.me.beepme.Entities.Order;
import server.beep.me.beepme.Services.BusinessLogic;

@Component
public class MessageListener {

    @Autowired
    BusinessLogic backend;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(OrderMessage order) {
        Order savedOrder= backend.saveOrder(order);
        
        if (savedOrder == null) {
            System.out.println("Erro!");
        } else {
            System.out.println(savedOrder);
        }
    }
    
}
