package com.epam.manage.dao.impl.hibernate;


import com.epam.manage.dao.IAuthorDao;
import com.epam.manage.model.entity.Author;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Profile("Hibernate")
@Transactional(rollbackFor = {Exception.class})
public class AuthorDaoImpl implements IAuthorDao {

    @Autowired
    private SessionFactory sessionFactory;


    public Long insert(Author author) {


        sessionFactory.getCurrentSession().save(author);

        return author.getAuthorId();
    }

    public void delete(Long id) {



        Query query = sessionFactory.getCurrentSession().getNamedQuery("Author.expiredAuthor");

        query.setParameter("authorId",id);

        query.executeUpdate();


    }

    public Author findById(Long id) {

        return (Author)sessionFactory.getCurrentSession().get(Author.class, id);
    }

    public void update(Author tag) {

        sessionFactory.getCurrentSession().update(tag);

    }

    public List<Author> findAll() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Author.class);
        criteria.addOrder(Order.asc("name"));


        return  criteria.list();

    }


    public List<Author> findAllActive() {

         Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Author.class);
         criteria.add(Restrictions.isNull("expired"));
         criteria.addOrder(Order.asc("name"));


        return  criteria.list();

    }



}
