package com.iviberberi.sdjpajdbc.hibernate.dao;

import com.iviberberi.sdjpajdbc.hibernate.domain.AuthorHB;

import java.util.List;

public interface AuthorDao {
    List<AuthorHB> findAll();

    List<AuthorHB> listAuthorByLastNameLike(String lastname);

    AuthorHB getById(Long id);

    AuthorHB findAuthorByName(String firstName, String lastName);

    AuthorHB saveNewAuthor(AuthorHB author);

    AuthorHB updateAuthor(AuthorHB saved);

    void deleteAuthorById(Long id);

    AuthorHB findAuthorByNameCriteria(String firstname, String lastname);
}
