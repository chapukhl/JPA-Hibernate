package com.epam.manage.dao;

import com.epam.manage.exception.DaoException;
import com.epam.manage.model.entity.Tag;

import java.util.List;


public interface ITagDao {


    /**Find All tags in db and return list of tags objects
     * @return full list of tags in appropriate table in database
     */
    List<Tag> findAll();

    /**find tag in bd by particular id
     * @param id - unique tag identifier
     * @return -tag from table if id exists, else null
     */
    Tag findById(Long id) ;

    /**delete tag row from db with particular id
     * @param id-unique tag identifier
     */
    void delete(Long id);

    /**insert row in bd appropriate tag object in parameter
     * @param tag-object to insertion in database
     */
    Long insert(Tag tag) ;


    /**update existing tag's row in bd
     * @param tag-object to update in database
     */
    void update(Tag tag) ;

    /**find the tags of news with identifier in parameter
     * @param newsId -unique news identifier
     * @return -full list of tags for appropriate news with newsId
     */
    //List<Tag> findTagsByNewsId(Long newsId) throws DaoException;
}
