package com.iviberberi.sdjpajdbc.hibernate.dao;

import com.iviberberi.sdjpajdbc.hibernate.domain.AuthorHB;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<AuthorHB> findAll() {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<AuthorHB> typedQuery = em.createNamedQuery("author_find_all", AuthorHB.class);

            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<AuthorHB> listAuthorByLastNameLike(String lastName) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createQuery("SELECT a from AuthorHB a where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            List<AuthorHB> authors = query.getResultList();

            return authors;
        } finally {
            em.close();
        }
    }

    @Override
    public AuthorHB getById(Long id) {
        return getEntityManager().find(AuthorHB.class, id);
    }

    @Override
    public AuthorHB findAuthorByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();
//        TypedQuery<AuthorHB> query = getEntityManager().createQuery("SELECT a FROM AuthorHB a " +
//                "WHERE a.firstName = :first_name AND a.lastName = :last_name", AuthorHB.class);

        TypedQuery<AuthorHB> query = em.createNamedQuery("find_by_name", AuthorHB.class);

        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }

    @Override
    public AuthorHB findAuthorByNameCriteria(String firstname, String lastname) {

        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AuthorHB> criteriaQuery = criteriaBuilder.createQuery(AuthorHB.class);

            Root<AuthorHB> root = criteriaQuery.from(AuthorHB.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<AuthorHB> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(firstNameParam, firstname);
            typedQuery.setParameter(lastNameParam, lastname);

            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public AuthorHB saveNewAuthor(AuthorHB author) {
        EntityManager em = getEntityManager();
//        em.joinTransaction();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();

        return author;
    }

    @Override
    public AuthorHB updateAuthor(AuthorHB authorHB) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(authorHB);
        em.flush();
        em.clear();
        return authorHB;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AuthorHB authorHB = em.find(AuthorHB.class, id);
        em.remove(authorHB);
        em.flush();
        em.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
