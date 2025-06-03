package com.library_manager.api.models;

import com.library_manager.api.models.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private BookModel book;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserModel user;

    @Column
    private LocalDate rentalDate;

    @Column
    private LocalDate expectedReturnDate;

    @Column
    private LocalDate realReturnDate;


    @Column
    private StatusEnum status;
}
