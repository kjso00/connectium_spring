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
                    newUser.setUsername(email);
                    newUser.setRole("ROLE_USER");
                    newUser.setSocialLogin(true);
                    newUser.setPassword("");
                    return userRepository.save(newUser);
                });
    }

    public User createRegularUser(String username, String email, String password, PasswordEncoder passwordEncoder) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setUsername(username);
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
