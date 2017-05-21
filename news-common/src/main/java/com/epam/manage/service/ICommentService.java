package com.epam.manage.service;

import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Comment;

import java.util.List;

public interface ICommentService {


    /**Find All comments in db and return list of comments objects
     * @return full list of comments in appropriate table in database
     */
    List<Comment> findAll() throws ServiceException;

    /**find comment in bd by particular id
     * @param id - unique comment identifier
     * @return -comment from table if id exists, else null
     */
    Comment findById(Long id) throws ServiceException;

    /**delete comment row from db with particular id
     * @param id-unique comment identifier
     */
    void delete(Long id) throws ServiceException;

    /**insert row in bd appropriate comment object in parameter
     * @param comment-object to insertion in database
     */
    Long insert(Comment comment) throws ServiceException;

    /**update existing comment's row in bd
     * @param comment-object to update in database
     */
    void update(Comment comment) throws ServiceException;

//    /**find the comments of news with identifier in parameter
//     * @param newsIds-unique news identifier
//     * @return comments list for appropriate news with newsId
//     */
//    List<Comment> findCommentsByNewsId(Long newsIds) throws ServiceException;
}
