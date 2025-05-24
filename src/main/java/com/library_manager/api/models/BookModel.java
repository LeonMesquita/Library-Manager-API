package com.library_manager.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    @Column(length = 20)
    private String genre;

    @Column(nullable = false)
    private Integer amountTotal;

    @Column
    private Integer amountAvailable;

    @Column(nullable = false)
    private boolean active = true;
}
