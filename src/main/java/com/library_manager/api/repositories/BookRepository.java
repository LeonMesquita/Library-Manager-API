package com.library_manager.api.repositories;

import com.library_manager.api.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {
    Optional<BookModel> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
}
