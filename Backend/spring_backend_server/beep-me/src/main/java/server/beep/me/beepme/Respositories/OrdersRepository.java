package server.beep.me.beepme.Respositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import server.beep.me.beepme.Entities.Order;
import server.beep.me.beepme.Entities.Restaurant;
import server.beep.me.beepme.Entities.State;

public interface OrdersRepository extends CrudRepository<Order, Integer>{
    
    List<Order> findByRestaurant(Restaurant rest, Sort sort);
    List<Order> findByState(State state);
    List<Order> findByOrderedTime(LocalDateTime orderedTime);

}
