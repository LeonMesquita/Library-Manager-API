package com.library_manager.api.services;

import com.library_manager.api.dtos.BookDTO;
import com.library_manager.api.exceptions.GenericConflictException;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.repositories.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
