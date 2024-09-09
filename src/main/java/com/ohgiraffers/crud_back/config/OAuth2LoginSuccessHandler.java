package com.ohgiraffers.crud_back.config;

import com.ohgiraffers.crud_back.model.entity.User;
import com.ohgiraffers.crud_back.service.UserService;
import com.ohgiraffers.crud_back.utils.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public OAuth2LoginSuccessHandler(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = extractEmail(oauth2User);
        User user = userService.createOrUpdateSocialUser(email);
        String token = jwtTokenUtil.generateToken(user.getUsername());
        String redirectUrl = "http://localhost:8081/oauth2/redirect?token=" + token;
        response.sendRedirect(redirectUrl);
    }

    private String extractEmail(OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        if (email == null) {
            Object response = oauth2User.getAttribute("response");
            if (response instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> responseMap = (Map<String, Object>) response;
                email = (String) responseMap.get("email");
            }
        }
        if (email == null) {
            throw new RuntimeException("Failed to extract email from OAuth2User");
        }
        return email;
    }
}