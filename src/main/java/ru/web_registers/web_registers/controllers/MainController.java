package ru.web_registers.web_registers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.web_registers.web_registers.model.User;
import ru.web_registers.web_registers.repository.UserRepository;
import java.util.List;

import java.io.IOException;

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

        // Проверяем роль пользователя
        if (user.getRole().getName().equals("admin")) {
            List<User> allUsers = userRepository.findAll();
            model.addAttribute("allUsers", allUsers);
        }

        // Передаем данные пользователя в модель
        model.addAttribute("user", user);

        return "main";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Завершаем сессию и очищаем контекст безопасности
        request.logout();
        SecurityContextHolder.clearContext();

        // Удаляем все куки
        for (Cookie cookie : request.getCookies()) {
             cookie.setMaxAge(0);
             cookie.setPath("/");
             response.addCookie(cookie);
        }

        return "redirect:/login";
    }
}
