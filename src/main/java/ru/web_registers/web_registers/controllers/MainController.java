package ru.web_registers.web_registers.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.web_registers.web_registers.model.User;
import ru.web_registers.web_registers.repository.UserRepository;

@Controller
public class MainController {

    private final UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/main")
    public String main(Model model) {
        // Получаем текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Находим пользователя в базе данных
        User user = userRepository.findByUsername(currentUsername);

        // Передаем данные пользователя в модель
        model.addAttribute("user", user);

        return "main";
    }
}
