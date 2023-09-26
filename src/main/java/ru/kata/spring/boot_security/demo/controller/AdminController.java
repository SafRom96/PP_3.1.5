package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

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
    public String add(ModelMap model, UserDto userDto) {
        userService.existByEmail(userDto.getEmail());
        userService.add(userDto);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "users";
    }

    @GetMapping("/update/{id}")
    public String update(ModelMap model, @PathVariable Long id) {
        userService.existById(id);
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.getRoles());
        return "update-user";
    }

    @PatchMapping("/update")
    public String update(ModelMap model, User user, @RequestParam String role) {
        userService.existById(user.getId());
        userService.update(user, role);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "users";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(ModelMap model, @PathVariable Long id) {
        userService.existById(id);
        userService.remove(id);
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roles", roleService.getRoles());
        return "users";
    }
}