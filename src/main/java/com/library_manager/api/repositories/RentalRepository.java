package com.library_manager.api.repositories;

import com.library_manager.api.models.RentalModel;
import com.library_manager.api.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {

    Page<RentalModel> findAllByUser(UserModel user, Pageable pageable);
}
