package com.library_manager.api.controllers;


import com.library_manager.api.controllers.utils.ProtectedEndpoint;
import com.library_manager.api.dtos.BookDTO;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@Tag(name = "books")
public class BookController {

    @Autowired
    BookService bookService;

    @Operation(summary = "Cria um novo livro")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe um livro com esse ISBN"),
            @ApiResponse(responseCode = "400", description = "Erros de validação do BookDTO"),

    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookModel> createBook(@RequestBody @Valid BookDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(body));
    }



    @Operation(summary = "Retorna todos os livros com paginação")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livros retornados com sucesso"),
    })
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



    @Operation(summary = "Busca um livro pelo ID")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),

    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findById(id));
    }



    @Operation(summary = "Atualiza um livro")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe um livro com esse ISBN"),
            @ApiResponse(responseCode = "400", description = "Quantidade total menor que a quantidade de aluguéis ativos"),
            @ApiResponse(responseCode = "400", description = "Erros de validação do BookDTO"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.update(id, body));
    }



    @Operation(summary = "Deleta um livro")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BookModel> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
