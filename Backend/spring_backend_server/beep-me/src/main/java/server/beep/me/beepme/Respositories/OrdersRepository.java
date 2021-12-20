package server.beep.me.beepme.Respositories;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import server.beep.me.beepme.Entities.Order;
import server.beep.me.beepme.Entities.Restaurant;

public interface OrdersRepository extends CrudRepository<Order, Integer>{
    
    List<Order> findByRestaurant(Restaurant rest, Sort sort);

}
