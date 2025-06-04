package com.library_manager.api.repositories;

import com.library_manager.api.models.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
    Optional<BookModel> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    @Query("SELECT l FROM BookModel l WHERE l.active = true")
    Page<BookModel> listAllActiveBooks(Pageable pageable);
}
