package com.epam.manage.dao.impl.hibernate;


import com.epam.manage.dao.ICommentDao;
import com.epam.manage.model.entity.Comment;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Profile("Hibernate")
@Transactional(rollbackFor = {Exception.class})
public class CommentDaoImpl implements ICommentDao {

    @Autowired
    private SessionFactory sessionFactory;


    public Long insert(Comment comment) {


        sessionFactory.getCurrentSession().save(comment);
        comment.getNews().getComments().add(comment);


        return comment.getCommentId();
    }

    public void delete(Long id) {


        sessionFactory.getCurrentSession().delete((findById(id)));

    }

    public Comment findById(Long id) {

        return (Comment)sessionFactory.getCurrentSession().get(Comment.class, id);

    }

    public void update(Comment comment) {


        sessionFactory.getCurrentSession().update(comment);

    }

    public List<Comment> findAll() {


        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Comment.class);

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();

    }


}
