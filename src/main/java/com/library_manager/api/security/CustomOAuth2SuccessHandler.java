package com.library_manager.api.security;

import com.library_manager.api.models.UserModel;
import com.library_manager.api.models.enums.ProfileEnum;
import com.library_manager.api.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("given_name");

        userRepository.findByEmail(email).orElseGet(() -> {
            UserModel user = new UserModel();
            user.setName(name);
            user.setEmail(email);
            user.setPassword("");
            user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
            return userRepository.save(user);
        });


        String token = jwtUtil.generateToken(email, jwtUtil.getTokenExpirationHour());
        String refreshToken = jwtUtil.generateToken(email, jwtUtil.getRefreshTokenExpirationHour());

//        response.sendRedirect("http://localhost:3000/oauth2-success?token=" + token + "&refresh=" + refreshToken);
        response.addHeader(
                "token", token
        );
        response.addHeader(
                "refreshToken", refreshToken
        );
    }
}
