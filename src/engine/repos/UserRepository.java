package engine.repos;

import engine.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select u FROM User u WHERE u.email = :value")
    User findByEmail(@Param("value") String value);
}
