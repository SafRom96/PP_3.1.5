package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String listUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "user";
    }

    @PostMapping("/add")
    public String add(ModelMap model, User user) {
        userService.add(user);
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping("/update/{id}")
    public String update(ModelMap model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        return "update-user";
    }

    @PatchMapping("/update")
    public String update(ModelMap model, User user) {
        userService.update(user);
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(ModelMap model, @PathVariable Long id) {
        userService.remove(id);
        model.addAttribute("users", userService.listUsers());
        return "users";
    }
}