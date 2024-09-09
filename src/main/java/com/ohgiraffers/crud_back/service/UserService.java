package com.ohgiraffers.crud_back.service;

import com.ohgiraffers.crud_back.model.entity.User;
import com.ohgiraffers.crud_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createOrUpdateSocialUser(String email) {
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.setSocialLogin(true);
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setUsername(email); // You might want to generate a unique username
                    newUser.setName(""); // Set default name for social login users
                    newUser.setPhoneNumber(""); // Set default phone number for social login users
                    newUser.setRole("ROLE_USER");
                    newUser.setSocialLogin(true);
                    newUser.setPassword("");
                    return userRepository.save(newUser);
                });
    }

    public User createRegularUser(String name, String phoneNumber, String username, String email, String password, PasswordEncoder passwordEncoder) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setName(name);
                    newUser.setPhoneNumber(phoneNumber);
                    newUser.setUsername(username);
                    newUser.setEmail(email);
                    newUser.setPassword(passwordEncoder.encode(password));
                    newUser.setRole("ROLE_USER");
                    newUser.setSocialLogin(false);
                    return userRepository.save(newUser);
                });
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
