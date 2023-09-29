package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String listUsers(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "users";
    }

    @PostMapping("/add")
    public String add(User user, @RequestParam(value = "selectedRoles", required = false) List<Integer> selectedRoles) {
        if (userService.existByEmail(user.getEmail()))
            throw new RuntimeException("Пользователь уже существует с email: " + user.getEmail());
        userService.add(user, selectedRoles);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String update(User user, @RequestParam(value = "selectedRoles", required = false) List<Integer> selectedRoles) {
        if (!userService.existById(user.getId()))
            throw new RuntimeException("Пользователь не найден с id: " + user.getId());
        userService.update(user, selectedRoles);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (!userService.existById(id)) throw new RuntimeException("Пользователь не найден с id: " + id);
        userService.remove(id);
        return "redirect:/admin";
    }
}