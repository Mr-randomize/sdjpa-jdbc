package com.iviberberi.sdjpajdbc.hibernate.dao;

import com.iviberberi.sdjpajdbc.hibernate.domain.BookHB;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {
    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public BookHB findByISBN(String isbn) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<BookHB> query = em.createQuery("SELECT b FROM BookHB b WHERE b.isbn = :isbn", BookHB.class);
            query.setParameter("isbn", isbn);

            BookHB book = query.getSingleResult();
            return book;
        } finally {
            em.close();
        }
    }

    @Override
    public BookHB getById(Long id) {
        EntityManager em = getEntityManager();
        BookHB book = getEntityManager().find(BookHB.class, id);
        em.close();
        return book;
    }

    @Override
    public BookHB findBookByTitle(String title) {
        EntityManager em = getEntityManager();
        TypedQuery<BookHB> query = em
                .createQuery("SELECT b FROM BookHB b where b.title = :title", BookHB.class);
        query.setParameter("title", title);
        BookHB book = query.getSingleResult();
        em.close();
        return book;
    }

    @Override
    public BookHB saveNewBook(BookHB book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public BookHB updateBook(BookHB book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        BookHB savedBook = em.find(BookHB.class, book.getId());
        em.getTransaction().commit();
        em.close();
        return savedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        BookHB book = em.find(BookHB.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<BookHB> findAll() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<BookHB> query = em.createNamedQuery("find_all_books", BookHB.class);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
