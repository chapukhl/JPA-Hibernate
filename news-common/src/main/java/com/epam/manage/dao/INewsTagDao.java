package com.epam.manage.dao;

import com.epam.manage.exception.DaoException;

/**
 * Created by Luka_Chapukhin on 7/1/2015.
 */
public interface INewsTagDao {


    /**create rows in db which connecting news with tags
     * @param newsId-unique news identifier
     * @param tagIds-unique tag identifiers
     */
    public void attachTagsToNews(Long newsId, Long[] tagIds);

    /**delete rows in db which connecting news with tags
     * @param newsId-unique news identifier
     */
    public void detachTagsToNews(Long newsId);
}
