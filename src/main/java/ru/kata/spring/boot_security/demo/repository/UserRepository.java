package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByRolesContains(Role role);
}