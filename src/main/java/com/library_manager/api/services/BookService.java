package com.library_manager.api.services;

import com.library_manager.api.dtos.BookDTO;
import com.library_manager.api.exceptions.GenericConflictException;
import com.library_manager.api.exceptions.GenericNotFoundException;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.repositories.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public BookModel save(BookDTO dto) {
        boolean exists = bookRepository.existsByIsbn(dto.getIsbn());
        if (exists) {
            throw new GenericConflictException("Já existe um livro com o ISBN " + dto.getIsbn());
        }
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(dto, bookModel);
        bookModel.setAmountAvailable(dto.getAmountTotal());

        return bookRepository.save(bookModel);
    }

    public Page<BookModel> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public BookModel findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Livro com o id " + id + " não encontrado!")
        );
    }


    public BookModel update(Long id, BookDTO dto) {
        BookModel bookModel = this.findById(id);
        Optional<BookModel> existingBook = bookRepository.findByIsbn(dto.getIsbn());
        if (existingBook.isPresent() && !Objects.equals(existingBook.get().getId(), bookModel.getId())) {
            throw new GenericConflictException("Já existe um livro com o ISBN " + dto.getIsbn());
        }
        //TODO: não deixar atualizar caso a quantidade total seja menor que a quantidade de aluguéis ativos
        BeanUtils.copyProperties(dto, bookModel, "id");
        return bookRepository.save(bookModel);
    }


}
