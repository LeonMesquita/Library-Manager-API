package com.library_manager.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalApprovalDTO {
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "A data de retorno é obrigatória")
    private String expectedReturnDate;
}
