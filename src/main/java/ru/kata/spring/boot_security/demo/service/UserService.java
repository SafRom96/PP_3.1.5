package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User getUserByPrincipal(Principal principal);

    void add(User user, List<Integer> selectedRoles);

    List<User> listUsers();

    void update(User user, List<Integer> selectedRoles);

    void remove(Long id);

    User findById(Long id);

    boolean existById(Long id);

    boolean existByEmail(String email);
}