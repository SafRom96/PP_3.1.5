package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class DataInit {
    private final UserService userService;
    private final RoleService roleService;

    public DataInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void start() {
        Role adminRole = roleService.add(new Role("ROLE_ADMIN"));
        roleService.add(new Role("ROLE_USER"));
        User admin = new User(
                "Ivan",
                "Ivanov",
                "admin@gmail.com",

                "admin",
                98,
                adminRole);
        userService.add(admin);
    }
}
