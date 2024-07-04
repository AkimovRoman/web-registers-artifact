package ru.web_registers.web_registers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.web_registers.web_registers.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
