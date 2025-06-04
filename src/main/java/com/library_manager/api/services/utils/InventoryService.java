package com.library_manager.api.services.utils;

import com.library_manager.api.models.BookModel;
import com.library_manager.api.models.RentalModel;
import com.library_manager.api.models.enums.StatusEnum;
import com.library_manager.api.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    RentalRepository rentalRepository;

    public int calculateAmountAvailable(BookModel book) {
        List<RentalModel> approvedRentals = rentalRepository.findAllByBookAndStatus(book, StatusEnum.APPROVED);
        return book.getAmountTotal() - approvedRentals.size();
    }
}