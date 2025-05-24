package com.library_manager.api.controllers;


import com.library_manager.api.dtos.BookDTO;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;


    @PostMapping
    private ResponseEntity<BookModel> createBook(@RequestBody @Valid BookDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(body));
    }
}
