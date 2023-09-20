package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.enums.Role;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    @Transactional
    @Override
    public void add(User user) {
        User admin = userRepository.findByRolesContains(Role.ROLE_ADMIN);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (admin == null) {
            user.getRoles().add(Role.ROLE_ADMIN);
        } else {
            user.getRoles().add(Role.ROLE_USER);
        }
        userRepository.save(user);
        log.info("save user with email {}", user.getEmail());
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void update(User updateUser) {
        Optional<User> user = userRepository.findById(updateUser.getId());
        if (user.isPresent()) {
            User newUser = user.get();
            newUser.setFirstName(updateUser.getFirstName());
            newUser.setLastName(updateUser.getLastName());
            newUser.setEmail(updateUser.getEmail());
        } else {
            updateUser.setActive(true);
            updateUser.setPassword("0000");
            userRepository.save(updateUser);
        }
    }

    @Transactional
    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
}