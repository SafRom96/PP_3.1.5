package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @GetMapping("currentUser")
    public ResponseEntity<User> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserByPrincipal(principal));
    }

    @GetMapping("roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.getRoles());
    }

    @PostMapping("/new")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        if (userService.existByEmail(user.getEmail()))
            throw new RuntimeException("Пользователь уже существует с email: " + user.getEmail());
        userService.add(user);
        return ResponseEntity.ok(userService.findUserByEmail(user.getEmail()));
    }

    @PatchMapping("edit")
    public ResponseEntity<User> update(@RequestBody User user) {
        if (!userService.existById(user.getId()))
            throw new RuntimeException("Пользователь не найден с id: " + user.getId());
        userService.update(user);
        return ResponseEntity.ok(userService.findUserByEmail(user.getEmail()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        if (!userService.existById(id)) throw new RuntimeException("Пользователь не найден с id: " + id);
        userService.remove(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}