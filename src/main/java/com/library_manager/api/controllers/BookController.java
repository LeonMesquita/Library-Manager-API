package com.library_manager.api.controllers;


import com.library_manager.api.dtos.BookDTO;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BookModel> createBook(@RequestBody @Valid BookDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(body));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<Page<BookModel>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<BookModel> books = bookService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.update(id, body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BookModel> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
