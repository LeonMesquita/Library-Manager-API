package com.library_manager.api.controllers;

import com.library_manager.api.controllers.utils.ProtectedEndpoint;
import com.library_manager.api.dtos.RentalApprovalDTO;
import com.library_manager.api.dtos.RentalDTO;
import com.library_manager.api.dtos.RentalReturnDTO;
import com.library_manager.api.models.RentalModel;
import com.library_manager.api.services.RentalService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
@Tag(name = "rentals")
public class RentalController {
    @Autowired
    private RentalService rentalService;


    @Operation(summary = "Cria uma solicitação de empréstimo")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível emprestar um livro deletado"),
            @ApiResponse(responseCode = "400", description = "Sem estoque disponível"),
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<RentalModel> createRental(@RequestBody @Valid RentalDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.save(body));
    }



    @Operation(summary = "Retorna todos os empréstimos do usuário com paginação")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    })
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




    @Operation(summary = "Retorna todos os empréstimos com paginação")
    @ProtectedEndpoint
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<RentalModel>> getAllRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<RentalModel> rentals = rentalService.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }




    @Operation(summary = "Aprova uma solicitação de empréstimo")
    @PreAuthorize("hasRole('ADMIN')")
    @ProtectedEndpoint
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo aprovado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado"),
            @ApiResponse(responseCode = "400", description = "Este empréstimo já foi aprovado"),
            @ApiResponse(responseCode = "400", description = "Não é possível atribuir uma data que já passou"),
            @ApiResponse(responseCode = "400", description = "Sem estoque disponível"),
    })
    @PutMapping("/{id}/approve")
    public ResponseEntity<RentalModel> approveRental(@PathVariable Long id, @RequestBody @Valid RentalApprovalDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.approveRental(id, body));
    }



    @Operation(summary = "Rejeita uma solicitação de empréstimo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo rejeitado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado"),
            @ApiResponse(responseCode = "400", description = "Este empréstimo já foi retornado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @ProtectedEndpoint
    @PutMapping("/{id}/reject")
    public ResponseEntity<RentalModel> rejectRental(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.rejectRental(id));
    }



    @Operation(summary = "Faz a devolução de um empréstimo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo devolvido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado"),
            @ApiResponse(responseCode = "400", description = "Não é possível devolver um empréstimo que não está aprovado"),
            @ApiResponse(responseCode = "400", description = "Não é possível atribuir uma data futura"),
            @ApiResponse(responseCode = "400", description = "A data de retorno não pode ser anterior à data do empréstimo")

    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ProtectedEndpoint
    @PutMapping("/{id}/return")
    public ResponseEntity<RentalModel> returnRental(@PathVariable Long id, @RequestBody @Valid RentalReturnDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.returnRental(id, body));
    }

}
