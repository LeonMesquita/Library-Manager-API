package com.library_manager.api.repositories;

import com.library_manager.api.models.BookModel;
import com.library_manager.api.models.RentalModel;
import com.library_manager.api.models.UserModel;
import com.library_manager.api.models.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {

    Page<RentalModel> findAllByUser(UserModel user, Pageable pageable);

    List<RentalModel> findAllByBookAndStatus(BookModel book, StatusEnum status);
}
