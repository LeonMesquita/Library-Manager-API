package com.library_manager.api.controllers;
import com.library_manager.api.dtos.LoginDTO;
import com.library_manager.api.dtos.RequestRefreshDTO;
import com.library_manager.api.dtos.TokenResponseDTO;
import com.library_manager.api.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Operation(summary = "Faz login de usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Email ou senha incorretos"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor"),
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @Operation(summary = "Obtém um novo token através do refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh token validado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Refresh token ausente"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor"),
    })
    @PostMapping(value = "/refresh-token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@RequestBody RequestRefreshDTO body) {
        return ResponseEntity.ok(authService.refreshToken(body));
    }
}

