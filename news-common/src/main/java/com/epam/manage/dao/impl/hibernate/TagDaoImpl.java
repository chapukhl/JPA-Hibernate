package com.epam.manage.dao.impl.hibernate;


import com.epam.manage.dao.ITagDao;
import com.epam.manage.model.entity.Tag;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Profile("Hibernate")
@Transactional(rollbackFor = {Exception.class})
public class TagDaoImpl implements ITagDao {

    @Autowired
    private SessionFactory sessionFactory;

     public Long insert(Tag tag) {


       sessionFactory.getCurrentSession().save(tag);

        return tag.getTagId();
    }

    public void delete(Long id) {


        sessionFactory.getCurrentSession().delete(findById(id));

    }

    public Tag findById(Long id) {

        return (Tag)sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    public void update(Tag tag) {

        sessionFactory.getCurrentSession().update(tag);

    }

    public List<Tag> findAll() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Tag.class);

        criteria.addOrder(Order.asc("tagName"));

        return criteria.list();
    }

}
