package com.epam.manage.dao.impl.jpa;


import com.epam.manage.dao.IAuthorDao;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Author;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import java.util.List;


@Profile("JPA")
@Transactional(rollbackFor = {Exception.class})
public class AuthorDaoImpl implements IAuthorDao {

    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;


    public Long insert(Author author) {


        entityManager.persist(author);

        return author.getAuthorId();
    }

    public void delete(Long id) {

        Query query = entityManager.createNamedQuery("Author.expiredAuthor");

        query.setParameter("authorId",id);

        query.executeUpdate();


    }

    public Author findById(Long id) {
        return entityManager.find(Author.class, id);
    }

    public void update(Author tag) {

        entityManager.merge(tag);

    }

    public List<Author> findAll() {

        Query namedQuery = entityManager.createNamedQuery("Author.getAll");
        return namedQuery.getResultList();

    }


    public List<Author> findAllActive() {

        Query namedQuery = entityManager.createNamedQuery("Author.getAllActive");

        return namedQuery.getResultList();

    }



}
