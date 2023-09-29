package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.security.Principal;
import java.util.List;

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
    public void add(User user, List<Integer> selectedRoles) {
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        for (Integer id : selectedRoles) {
            user.addRole(roleRepository.getById(id));
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
    public void update(User updateUser, List<Integer> selectedRoles) {
        User user = userRepository.getById(updateUser.getId());
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        user.setAge(updateUser.getAge());
        if (!updateUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }
        if (selectedRoles != null) {
            user.getRoles().clear();
            for (Integer id : selectedRoles) {
                user.addRole(roleRepository.getById(id));
            }
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
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}