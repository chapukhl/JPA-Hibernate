package com.epam.manage.service;

/**
 * Created by Luka_Chapukhin on 7/1/2015.
 */
public interface INewsAuthorService {

    /**create row in db which connecting news with author
     * @param newsId-unique   news identifier
     * @param authorId-unique author identifier
     */
    void attachAuthorToNews(Long newsId, Long authorId);

    /**delete row in db which connecting news with author
     * @param newsId-unique news identifier
     */
    void detachAuthorToNews(Long newsId);
}
