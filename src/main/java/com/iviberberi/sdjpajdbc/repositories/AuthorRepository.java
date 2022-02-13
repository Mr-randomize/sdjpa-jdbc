package com.iviberberi.sdjpajdbc.repositories;

import com.iviberberi.sdjpajdbc.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
