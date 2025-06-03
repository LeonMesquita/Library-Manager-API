package com.library_manager.api.services;

import com.library_manager.api.dtos.RentalApprovalDTO;
import com.library_manager.api.dtos.RentalDTO;
import com.library_manager.api.dtos.RentalReturnDTO;
import com.library_manager.api.exceptions.GenericBadRequestException;
import com.library_manager.api.exceptions.GenericNotFoundException;
import com.library_manager.api.models.BookModel;
import com.library_manager.api.models.RentalModel;
import com.library_manager.api.models.UserModel;
import com.library_manager.api.models.enums.StatusEnum;
import com.library_manager.api.repositories.BookRepository;
import com.library_manager.api.repositories.RentalRepository;
import com.library_manager.api.security.UserSpringSecurity;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;


    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;


    public RentalModel save(RentalDTO dto) {
        BookModel book = bookService.findById(dto.getBookId());
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = userService.findById(userSpringSecurity.getId());

//        if (book.getAmountAvailable() < 1) {
//            throw new GenericBadRequestException("Nenhum estoque disponível!");
//        }
        RentalModel rental = new RentalModel();
        rental.setBook(book);
        rental.setUser(user);
        rental.setStatus(StatusEnum.PENDING);
        return rentalRepository.save(rental);
    }


    public RentalModel approveRental(Long id, RentalApprovalDTO dto) {
        RentalModel rental = this.findById(id);
        if (rental.getStatus() == StatusEnum.APPROVED) {
            throw new GenericBadRequestException("Este empréstimo já foi aprovado!");
        }

        LocalDate expectedReturnDate = this.convertToDate(dto.getExpectedReturnDate());
        if (expectedReturnDate.isBefore(LocalDate.now())) {
            throw new GenericBadRequestException("Não é possível atribuir uma data que já passou!");
        }

        BookModel book = rental.getBook();
        if (book.getAmountAvailable() < 1) {
            throw new GenericBadRequestException("Este livro não tem estoque disponível!");
        }

        rental.setRentalDate(LocalDate.now());
        rental.setExpectedReturnDate(this.convertToDate(dto.getExpectedReturnDate()));
        rental.setStatus(StatusEnum.APPROVED);

        RentalModel savedRental = rentalRepository.save(rental);
        book.setAmountAvailable(this.calculateAmountAvailable(book));
        bookRepository.save(book);
        return savedRental;

    }

    public RentalModel rejectRental(Long id) {
        RentalModel rental = this.findById(id);
        if (rental.getStatus() == StatusEnum.RETURNED) {
            throw new GenericBadRequestException("Este empréstimo já foi retornado!");
        }
        StatusEnum prevStatus = rental.getStatus();

        rental.setStatus(StatusEnum.REJECTED);
        RentalModel savedRental = rentalRepository.save(rental);
        if (prevStatus == StatusEnum.APPROVED) {
            BookModel book = rental.getBook();
            book.setAmountAvailable(this.calculateAmountAvailable(book));
            bookRepository.save(book);
        }
        return savedRental;

    }

    public RentalModel returnRental(Long id, RentalReturnDTO dto) {
        RentalModel rental = this.findById(id);
        if (rental.getStatus() != StatusEnum.APPROVED) {
            throw new GenericBadRequestException("Não é possível devolver um empréstimo que não está aprovado!");
        }


        LocalDate realReturnDate = this.convertToDate(dto.getRealReturnDate());
        if (realReturnDate.isAfter(LocalDate.now())) {
            throw new GenericBadRequestException("Não é possível atribuir uma data futura!");
        }

        if (realReturnDate.isBefore(rental.getRentalDate())) {
            throw new GenericBadRequestException("A data de retorno não pode ser anterior à data do empréstimo!");
        }

        BookModel book = rental.getBook();
        
        rental.setStatus(StatusEnum.RETURNED);
        rental.setRealReturnDate(realReturnDate);
        RentalModel savedRental = rentalRepository.save(rental);
        book.setAmountAvailable(this.calculateAmountAvailable(book));
        bookRepository.save(book);
        return savedRental;

    }

    public Page<RentalModel> getMyRentals(Pageable pageable) {
        UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel user = userService.findById(userSpringSecurity.getId());
        return rentalRepository.findAllByUser(user, pageable);
    }

    public RentalModel findById(Long id) {
        return rentalRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Empréstimo não encontrado")
        );
    }


    public LocalDate convertToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            throw new GenericBadRequestException("A data deve estar no formato dd/MM/yyyy e ser válida.");
        }
    }

    public Integer calculateAmountAvailable(BookModel book) {
        List<RentalModel> approvedRentals = rentalRepository.findAllByBookAndStatus(book, StatusEnum.APPROVED);
        return book.getAmountTotal() - approvedRentals.size();
    }
}
