package com.library_manager.api.controllers;
import com.library_manager.api.dtos.LoginDTO;
import com.library_manager.api.dtos.RequestRefreshDTO;
import com.library_manager.api.dtos.TokenResponseDTO;
import com.library_manager.api.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RequestRefreshDTO body) {
        return ResponseEntity.ok(authService.refreshToken(body));
    }
}

