package ru.web_registers.web_registers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String login() {
        return "login"; // Возвращает имя шаблона (login.html)
    }
}