package com.library_manager.api.services;
import com.library_manager.api.dtos.LoginDTO;
import com.library_manager.api.dtos.RequestRefreshDTO;
import com.library_manager.api.dtos.TokenResponseDTO;
import com.library_manager.api.exceptions.AuthorizationException;
import com.library_manager.api.exceptions.GenericBadRequestException;
import com.library_manager.api.security.JWTUtil;
import com.library_manager.api.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JWTUtil jwtUtil;
    public TokenResponseDTO login(LoginDTO dto) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            Authentication authentication = authenticationManager.authenticate(authToken);
            UserSpringSecurity user = (UserSpringSecurity) authentication.getPrincipal();
            String accessToken = jwtUtil.generateToken(user.getUsername(), jwtUtil.getTokenExpirationHour());
            String refreshToken = jwtUtil.generateToken(user.getUsername(), jwtUtil.getRefreshTokenExpirationHour());

            return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO refreshToken(RequestRefreshDTO dto) {
        String refreshToken = dto.refreshToken();

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new GenericBadRequestException("Refresh token ausente");
        }

        String email = jwtUtil.isValidToken(refreshToken);

        if (email.isEmpty()) {
            throw new AuthorizationException("Refresh token inválido");
        }

        String newAccessToken = jwtUtil.generateToken(email, jwtUtil.getTokenExpirationHour());
        String newRefreshToken = jwtUtil.generateToken(email, jwtUtil.getRefreshTokenExpirationHour());

        return new TokenResponseDTO(newAccessToken, newRefreshToken);
    }
}
