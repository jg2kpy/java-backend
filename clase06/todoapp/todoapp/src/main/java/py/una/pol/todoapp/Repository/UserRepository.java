package py.una.pol.todoapp.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import py.una.pol.todoapp.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
