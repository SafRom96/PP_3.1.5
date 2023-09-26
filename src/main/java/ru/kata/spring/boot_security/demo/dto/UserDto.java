package ru.kata.spring.boot_security.demo.dto;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String password;
    private String role;
}
