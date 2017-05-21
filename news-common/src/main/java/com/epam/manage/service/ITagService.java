package com.epam.manage.service;

import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Tag;

import java.util.List;


public interface ITagService {

    /**Find All tags in db and return list of tags objects
     * @return full list of tags in appropriate table in database
     */
    List<Tag> findAll() throws ServiceException;

    /**find tag in bd by particular id
     * @param id - unique tag identifier
     * @return -tag from table if id exists, else null
     */
    Tag findById(Long id) throws ServiceException;

    /**delete tag row from db with particular id
     * @param id-unique tag identifier
     */
    void delete(Long id) throws ServiceException;

    /**insert row in bd appropriate tag object in parameter
     * @param tag-object to insertion in database
     */
    Long insert(Tag tag) throws ServiceException;


    /**update existing tag's row in bd
     * @param tag-object to update in database
     */
    void update(Tag tag) throws ServiceException;

//    /**find the tags of news with identifier in parameter
//     * @param newsId -unique news identifier
//     * @return -full list of tags for appropriate news with newsId
//     */
//    List<Tag> findTagsByNewsId(Long newsId) throws ServiceException;
}
