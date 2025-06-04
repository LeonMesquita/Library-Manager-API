package com.library_manager.api.services;

import com.library_manager.api.dtos.BookDTO;
import com.library_manager.api.exceptions.GenericConflictException;
import com.library_manager.api.exceptions.GenericNotFoundException;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.models.RentalModel;
import com.library_manager.api.models.enums.StatusEnum;
import com.library_manager.api.repositories.BookRepository;
import com.library_manager.api.repositories.RentalRepository;
import com.library_manager.api.services.utils.InventoryService;
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

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    InventoryService inventoryService;

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
        return bookRepository.listAllActiveBooks(pageable);
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

        List<RentalModel> approvedRentals = rentalRepository.findAllByBookAndStatus(bookModel, StatusEnum.APPROVED);

        if (dto.getAmountTotal() < approvedRentals.size()) {
            throw new GenericConflictException("A quantidade total não pode ser menor que a quantidade de aluguéis ativos!");
        }

        BeanUtils.copyProperties(dto, bookModel, "id");
        bookModel.setAmountAvailable(inventoryService.calculateAmountAvailable(bookModel));
        return bookRepository.save(bookModel);
    }


    public void delete(Long id) {
        BookModel book = this.findById(id);
        book.setActive(false);
        bookRepository.save(book);
    }


}
