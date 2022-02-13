package com.iviberberi.sdjpajdbc.hibernate.repositories;

import com.iviberberi.sdjpajdbc.hibernate.domain.BookHB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryHB extends JpaRepository<BookHB, Long> {
}
