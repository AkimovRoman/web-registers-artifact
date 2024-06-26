package ru.web_registers.web_registers.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.web_registers.web_registers.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}