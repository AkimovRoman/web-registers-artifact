package ru.web_registers.web_registers.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.web_registers.web_registers.model.Role;
import ru.web_registers.web_registers.model.User;
import ru.web_registers.web_registers.repository.RoleRepository;
import ru.web_registers.web_registers.repository.UserRepository;

import java.util.List;

@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin/users")
    public String getAllUsersAndRoles(Model model) {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Находим пользователя в базе данных
        User user = userRepository.findByUsername(currentUsername);

        // Добавляем текущего пользователя в модель
        model.addAttribute("user", user);

        // Получаем всех пользователей
        List<User> allUsers = userRepository.findAll();
        model.addAttribute("allUsers", allUsers);

        // Получаем все роли
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("allRoles", allRoles);

        return "users";
    }
}