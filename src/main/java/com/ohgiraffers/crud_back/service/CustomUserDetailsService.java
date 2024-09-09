package com.ohgiraffers.crud_back.service;

import com.ohgiraffers.crud_back.model.entity.User;
import com.ohgiraffers.crud_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

    public UserDetails loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> createSocialUser(email));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

    private User createSocialUser(String email) {
        // 소셜 로그인으로 처음 접속한 사용자를 위한 계정 생성 로직
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(email); // 또는 다른 고유한 username 생성 로직
        newUser.setPassword(UUID.randomUUID().toString()); // 임의의 비밀번호
        newUser.setRole("ROLE_USER");
        return userRepository.save(newUser);
    }
}
