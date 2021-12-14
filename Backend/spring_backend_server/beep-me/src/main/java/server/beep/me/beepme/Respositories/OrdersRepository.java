package server.beep.me.beepme.Respositories;

import org.springframework.data.repository.CrudRepository;

import server.beep.me.beepme.Entities.Order;

public interface OrdersRepository extends CrudRepository<Order, Integer>{
    
}
