package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User getUserByPrincipal(Principal principal);

    void add(UserDto user);

    void add(User user);

    List<User> listUsers();

    void update(User user, String role);

    void remove(Long id);

    User findById(Long id);

    void existById(Long id);

    void existByEmail(String email);
}