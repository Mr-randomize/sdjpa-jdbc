package com.iviberberi.sdjpajdbc.hibernate.dao;

import com.iviberberi.sdjpajdbc.hibernate.domain.BookHB;

import java.util.List;

public interface BookDao {

    BookHB findByISBN(String isbn);

    BookHB getById(Long id);

    BookHB findBookByTitle(String title);

    BookHB saveNewBook(BookHB book);

    BookHB updateBook(BookHB book);

    void deleteBookById(Long id);

    List<BookHB> findAll();
}
