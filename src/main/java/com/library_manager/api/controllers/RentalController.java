package com.library_manager.api.controllers;

import com.library_manager.api.dtos.RentalApprovalDTO;
import com.library_manager.api.dtos.RentalDTO;
import com.library_manager.api.models.RentalModel;
import com.library_manager.api.services.RentalService;
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
@RequestMapping("/rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<RentalModel> createRental(@RequestBody @Valid RentalDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.save(body));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/mine")
    public ResponseEntity<Page<RentalModel>> getAllMyRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<RentalModel> rentals = rentalService.getMyRentals(PageRequest.of(page, size, Sort.by(sortBy)));
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/approve")
    public ResponseEntity<RentalModel> approveRental(@PathVariable Long id, @RequestBody @Valid RentalApprovalDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.approveRental(id, body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reject")
    public ResponseEntity<RentalModel> rejectRental(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.rejectRental(id));
    }

}
