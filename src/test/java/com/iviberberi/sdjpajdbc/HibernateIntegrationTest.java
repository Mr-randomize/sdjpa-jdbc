package com.iviberberi.sdjpajdbc;

import com.iviberberi.sdjpajdbc.hibernate.dao.AuthorDao;
import com.iviberberi.sdjpajdbc.hibernate.dao.BookDao;
import com.iviberberi.sdjpajdbc.hibernate.domain.AuthorHB;
import com.iviberberi.sdjpajdbc.hibernate.domain.BookHB;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"com.iviberberi.sdjpajdbc.hibernate"})
public class HibernateIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void testFindAllAuthors() {
        List<AuthorHB> authors = authorDao.findAll();

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testFindBookByISBN() {
        BookHB book = new BookHB();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("ISBN TEST");

        BookHB saved = bookDao.saveNewBook(book);

        BookHB fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testListAuthorByLastNameLike() {
        List<AuthorHB> authors = authorDao.listAuthorByLastNameLike("Wall");

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }


    @Test
    void testDeleteBook() {
        BookHB book = new BookHB();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        BookHB saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        BookHB deleted = bookDao.getById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        BookHB book = new BookHB();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        AuthorHB author = new AuthorHB();
        author.setId(3L);

        book.setAuthorId(1L);
        BookHB saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        BookHB fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        BookHB book = new BookHB();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        AuthorHB author = new AuthorHB();
        author.setId(3L);

        book.setAuthorId(1L);
        BookHB saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        BookHB book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        BookHB book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }


    @Test
    void testDeleteAuthor() {
        AuthorHB author = new AuthorHB();
        author.setFirstName("john");
        author.setLastName("t");

        AuthorHB saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());


        AuthorHB deleted = authorDao.getById(saved.getId());
        assertThat(deleted).isNull();
        assertThat(authorDao.getById(saved.getId()));
    }

    @Test
    void testUpdateAuthor() {
        AuthorHB author = new AuthorHB();
        author.setFirstName("john");
        author.setLastName("t");

        AuthorHB saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Thompson");
        AuthorHB updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }

    @Test
    void testInsertAuthor() {
        AuthorHB author = new AuthorHB();
        author.setFirstName("john");
        author.setLastName("t222");

        AuthorHB saved = authorDao.saveNewAuthor(author);

        System.out.println("New Id is: " + saved.getId());

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testFindAllBooks() {
        List<BookHB> books = bookDao.findAll();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    void testFindBookByTitle() {
        BookHB book = new BookHB();
        book.setIsbn("1235" + RandomString.make());
        book.setTitle("TITLETEST2");

        BookHB saved = bookDao.saveNewBook(book);

        BookHB fetched = bookDao.findBookByTitle(book.getTitle());
        assertThat(fetched).isNotNull();

        bookDao.deleteBookById(saved.getId());
    }

    @Test
    void testGetAuthorByNameCriteria() {
        AuthorHB author = authorDao.findAuthorByNameCriteria("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        AuthorHB author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthor() {

        AuthorHB author = authorDao.getById(1l);

        assertThat(author.getId()).isNotNull();
    }
}