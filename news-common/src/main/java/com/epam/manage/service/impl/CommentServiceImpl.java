package com.epam.manage.service.impl;


import com.epam.manage.dao.ICommentDao;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Comment;
import com.epam.manage.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentDao commentDao;


    @Override
    public Long insert(Comment entity) throws ServiceException {

        try {
           return commentDao.insert(entity);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long commentId) throws ServiceException {

        try {
            commentDao.delete(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Comment findById(Long id) throws ServiceException {
        try {
            return commentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Comment> findAll() throws ServiceException {
        try {
            return commentDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public void update(Comment entity) throws ServiceException {
        try {
            commentDao.update(entity);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

//    @Override
//    public List<Comment> findCommentsByNewsId(Long newsId) throws ServiceException {
//        try {
//            return commentDao.findCommentsByNewsId(newsId);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//    }
}
