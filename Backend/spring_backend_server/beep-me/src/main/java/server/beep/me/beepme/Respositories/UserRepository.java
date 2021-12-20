package server.beep.me.beepme.Respositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import server.beep.me.beepme.Entities.User;

public interface UserRepository extends CrudRepository<User, Integer>{
    
    ArrayList<User> findUsersByUsername(String username);
}
