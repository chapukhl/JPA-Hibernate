package com.epam.manage.dao;

import com.epam.manage.exception.DaoException;
import com.epam.manage.model.entity.Comment;

import java.util.List;
import java.util.Set;


public interface ICommentDao {


    /**Find All comments in db and return list of comments objects
     * @return full list of comments in appropriate table in database
     */
   List<Comment> findAll() throws DaoException;

    /**find comment in bd by particular id
     * @param id - unique comment identifier
     * @return -comment from table if id exists, else null
     */
    Comment findById(Long id) throws DaoException;

    /**delete comment row from db with particular id
     * @param id-unique comment identifier
     */
    void delete(Long id) throws DaoException;

    /**insert row in bd appropriate comment object in parameter
     * @param comment-object to insertion in database
     */
    Long insert(Comment comment) throws DaoException;

    /**update existing comment's row in bd
     * @param comment-object to update in database
     */
    void update(Comment comment) throws DaoException;

    /**find the comments of news with identifier in parameter
     * @param newsId-unique news identifier
     * @return comments list for appropriate news with newsId
     */
  //  List<Comment> findCommentsByNewsId(Long newsId) throws DaoException;
}
