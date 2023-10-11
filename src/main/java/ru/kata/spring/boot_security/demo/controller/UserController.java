package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("currentUser")
    public ResponseEntity<User> getUser(Principal principal) {
        return ResponseEntity.ok(userService.getUserByPrincipal(principal));
    }
}
