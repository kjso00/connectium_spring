//package com.ohgiraffers.crud_back.controller.controller;
//
//import com.ohgiraffers.crud_back.model.entity.User;
//import com.ohgiraffers.crud_back.service.UserService;
//import com.ohgiraffers.crud_back.utils.JwtResponse;
//import com.ohgiraffers.crud_back.utils.JwtTokenUtil;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class OAuth2Controller {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/oauth2/callback/google")
//    public void googleCallback(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response) throws IOException {
//        handleOAuth2Login(oauth2User, response);
//    }
//
//    @GetMapping("/oauth2/callback/naver")
//    public void naverCallback(@AuthenticationPrincipal OAuth2User oauth2User, HttpServletResponse response) throws IOException {
//        handleOAuth2Login(oauth2User, response);
//    }
//
//    private void handleOAuth2Login(OAuth2User oauth2User, HttpServletResponse response) throws IOException {
//        String email = extractEmail(oauth2User);
//        User user = userService.createOrUpdateUser(email);
//        String token = jwtTokenUtil.generateToken(user.getUsername());
//
//        // 프론트엔드로 JWT 토큰 전달
//        String redirectUrl = "http://localhost:8081/oauth2/redirect?token=" + token;
//        response.sendRedirect(redirectUrl);
//    }
//
//    private String extractEmail(OAuth2User oauth2User) {
//        String email = oauth2User.getAttribute("email");
//        if (email == null) {
//            Object response = oauth2User.getAttribute("response");
//            if (response instanceof Map) {
//                @SuppressWarnings("unchecked")
//                Map<String, Object> responseMap = (Map<String, Object>) response;
//                email = (String) responseMap.get("email");
//            }
//        }
//        if (email == null) {
//            throw new RuntimeException("Failed to extract email from OAuth2User");
//        }
//        return email;
//    }
//}
//
