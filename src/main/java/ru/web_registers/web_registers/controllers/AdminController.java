package ru.web_registers.web_registers.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // Метод для получения всех пользователей и ролей
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

        // Возвращаем шаблон "users.html"
        return "users";
    }

    // Методы для работы с ролями (добавление, редактирование, удаление)
    @PostMapping("/admin/addRole")
    public String addRole(@RequestParam("name") String name, @RequestParam("description") String description) {
        Role newRole = new Role();
        newRole.setName(name);
        newRole.setDescription(description);

        roleRepository.save(newRole);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/editRole")
    public String editRole(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("description") String description) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role id: " + id));
        role.setName(name);
        role.setDescription(description);

        roleRepository.save(role);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/deleteRole")
    public String deleteRole(@RequestParam("id") Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid role id: " + id));
        roleRepository.delete(role);

        return "redirect:/admin/users";
    }

    // Методы для работы с пользователями (добавление, редактирование, удаление)
    @PostMapping("/admin/addUser")
    public String addUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role id: " + roleId));
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Возможно стоит хэшировать пароль в дальнейшем
        newUser.setRole(role);

        userRepository.save(newUser);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/editUser")
    public String editUser(@RequestParam("id") Long id, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") Long roleId) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role id: " + roleId));
        user.setUsername(username);
        user.setPassword(password); // Возможно стоит хэшировать пароль в дальнейшем
        user.setRole(role);

        userRepository.save(user);

        return "redirect:/admin/users";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        userRepository.delete(user);

        return "redirect:/admin/users";
    }
}
