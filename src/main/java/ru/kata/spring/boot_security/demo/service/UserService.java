package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User getUserByPrincipal(Principal principal);

    void add(User user);

    List<User> listUsers();

    void update(User user);

    void remove(Long id);

    User findById(Long id);
}