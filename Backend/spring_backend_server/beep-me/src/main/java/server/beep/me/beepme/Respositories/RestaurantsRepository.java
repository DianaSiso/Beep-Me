package server.beep.me.beepme.Respositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import server.beep.me.beepme.Entities.Restaurant;

public interface RestaurantsRepository extends CrudRepository<Restaurant, Integer>{

    List<Restaurant> findByName(String name);
    List<Restaurant> findByUserId(String user_id);

    
}
