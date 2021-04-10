package co.com.cfsm.prueba.user.repository;

import org.springframework.data.repository.CrudRepository;
import co.com.cfsm.prueba.user.model.User;

public interface UserRepository extends CrudRepository<User, String> {


}
