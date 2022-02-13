package com.iviberberi.sdjpajdbc.dao.v2;

import com.iviberberi.sdjpajdbc.domain.Author;
import com.iviberberi.sdjpajdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong(1));
        book.setIsbn(rs.getString(2));
        book.setPublisher(rs.getString(3));
        book.setTitle(rs.getString(4));
        Author author = new Author();
        author.setId(rs.getLong(5));
        book.setAuthor(author);
        return book;
    }
}
