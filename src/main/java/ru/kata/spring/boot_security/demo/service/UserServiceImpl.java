package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static ru.kata.spring.boot_security.demo.mapper.UserMapper.toUser;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName());
    }

    @Transactional
    @Override
    public void add(UserDto userDto) {
        User user = toUser(userDto);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (Objects.equals(userDto.getRole(), "ADMIN")) {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            Role roleUser = roleRepository.findByName("ROLE_USER");
            user.addRole(roleUser);
            user.addRole(roleAdmin);
        } else if (Objects.equals(userDto.getRole(), "USER")) {
            Role roleUser = roleRepository.findByName("ROLE_USER");
            user.addRole(roleUser);
        }
        userRepository.save(user);
        log.info("save user with email {}", user.getEmail());
    }

    @Transactional
    @Override
    public void add(User user) {
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void update(User updateUser, String role) {
        User user = userRepository.getById(updateUser.getId());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        user.setAge(updateUser.getAge());
        if (!updateUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        if (Objects.equals(role, "ADMIN")) {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            Role roleUser = roleRepository.findByName("ROLE_USER");
            user.addRole(roleUser);
            user.addRole(roleAdmin);
        } else if (Objects.equals(role, "USER")) {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            System.out.println("!!!!!!!!!   " + roleAdmin);
            user.deleteRole(roleAdmin);
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

    @Override
    public void existById(Long id) {
        if (!userRepository.existsById(id)) throw new RuntimeException("Пользователь не найден с id: " + id);
    }

    @Override
    public void existByEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new RuntimeException("Пользователь уже существует с email: " + email);
    }
}