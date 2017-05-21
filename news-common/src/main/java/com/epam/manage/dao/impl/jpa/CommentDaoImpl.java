package com.epam.manage.dao.impl.jpa;


import com.epam.manage.dao.ICommentDao;
import com.epam.manage.model.entity.Comment;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;



@Profile("JPA")
@Transactional(rollbackFor = {Exception.class})
public class CommentDaoImpl implements ICommentDao {

    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    public Long insert(Comment comment) {



        entityManager.persist(comment);
        comment.getNews().getComments().add(comment);


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
