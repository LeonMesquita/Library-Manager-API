package com.library_manager.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library_manager.api.models.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RentalDTO {

//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @NotNull(message = "A data de locação é obrigatória")
//    private String expectedReturnDate;
//
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @NotNull(message = "A data de locação é obrigatória")
//    private String rentalDate;
//
//    @NotBlank
//    private StatusEnum status;

    @NotNull
    private Long bookId;

//    @NotNull
//    private Long userId;
}
