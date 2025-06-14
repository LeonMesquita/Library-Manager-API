package com.library_manager.api.controllers;


import com.library_manager.api.controllers.utils.ProtectedEndpoint;
import com.library_manager.api.dtos.UserDTO;
import com.library_manager.api.models.UserModel;
import com.library_manager.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Tag(name = "users")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário com esse email"),
            @ApiResponse(responseCode = "400", description = "Erros de validação do UserDTO"),
            @ApiResponse(responseCode = "500", description = "Erro de servidor"),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(body));
    }

    @Operation(summary = "Obtém um usuário pelo ID")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }



    @Operation(summary = "Atualiza um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário com esse email"),
            @ApiResponse(responseCode = "400", description = "Erros de validação do UserDTO"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    })
    @ProtectedEndpoint
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateuser(@PathVariable Long id, @RequestBody @Valid UserDTO body) {
        return ResponseEntity.ok(userService.update(id, body));
    }

    @Operation(summary = "Deleta um usuário")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserModel> deleteuser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
