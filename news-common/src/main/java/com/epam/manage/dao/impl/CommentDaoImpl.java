package com.epam.manage.dao.impl;


import com.epam.manage.dao.ICommentDao;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;


@Service
@Transactional(rollbackFor = {Exception.class})
public class CommentDaoImpl implements ICommentDao {

    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    public Long insert(Comment comment) {


        //Comment commentFromDB = entityManager.merge(comment);
        entityManager.persist(comment);
        comment.getNews().getCommentsList().add(comment);


        return comment.getCommentId();
    }

    public void delete(Long id) {


        entityManager.remove(findById(id));

    }

    public Comment findById(Long id) {
        return entityManager.find(Comment.class, id);
    }

    public void update(Comment comment) {


        entityManager.merge(comment);

    }

    public List<Comment> findAll() {

        Query namedQuery = entityManager.createNamedQuery("Comment.getAll");

        return namedQuery.getResultList();

    }


}
