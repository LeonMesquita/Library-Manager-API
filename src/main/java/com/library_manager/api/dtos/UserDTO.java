package com.library_manager.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    @NotBlank
    @Size(min = 10, max = 30)
    @Email
    private String email;

}
