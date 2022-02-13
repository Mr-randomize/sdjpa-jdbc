package com.iviberberi.sdjpajdbc.repositories;

import com.iviberberi.sdjpajdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
