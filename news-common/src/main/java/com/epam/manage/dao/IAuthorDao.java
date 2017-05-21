package com.epam.manage.dao;

import com.epam.manage.exception.DaoException;
import com.epam.manage.model.entity.Author;

import java.util.List;


public interface IAuthorDao {

    /**Find All  authors in db and return list of authors objects
     * @return full list of authors in appropriate table in database
     */
    List<Author> findAll();

    /**Find All not expired authors in db and return list of authors objects
     * @return full list of authors in appropriate table in database
     */
    public List<Author> findAllActive();

    /**find author in bd by particular id
     * @param id - unique author identifier
     * @return -author from table if id exists, else null
     */
    Author findById(Long id);

    /**delete author row from db with particular id
     *
     * @param id-unique author identifier
     */
    void delete(Long id);

    /**insert row in bd appropriate author object in parameter
     *
     * @param author-object to insertion in database
     */
    Long insert(Author author);

    /**update existing author's row in bd
     *
     * @param author-object to update in database
     */
    void update(Author author) ;

    /**find the author of news with identifier in parameter
     * @param newsId-unique news identifier
     * @return author of news with newsId
     */


}
