package com.epam.manage.service;

/**
 * Created by Luka_Chapukhin on 7/1/2015.
 */
public interface INewsTagService {


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
