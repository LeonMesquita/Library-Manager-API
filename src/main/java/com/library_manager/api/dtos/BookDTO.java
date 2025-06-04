package com.library_manager.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {

    @NotBlank
    @Size(min = 5, max = 80)
    private String title;
    @NotBlank
    @Size(min = 5, max = 50)
    private String author;

    @NotBlank
    @Size(min = 13, max = 13)
    private String isbn;

    @NotBlank
    @Size(min = 3, max = 20)
    private String genre;


    @NotNull
    private Integer amountTotal;

}
