package com.epam.manage.service;




import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.News;


public interface IManageService {

    /**insert news in db and attach her with author and tags
     *
     * @param news news object for insert in bd
     * @param id-author unique  identifier  for attaching to news
     * @param tagIds- tag unique  identifiers for attaching to news
     */
    void createNews(News news, Long id, Long[] tagIds) throws ServiceException;

    /**update news and her attachments (author,tags)
     *
     * @param news news object for insert in bd
     * @param id-author unique  identifier  for attaching to news
     * @param tagIds- tag unique  identifiers for attaching to news
     */
    void updateNews(News news, Long id, Long[] tagIds) throws ServiceException;

    /**delete news and her attachments (author,tags)
     *
     * @param newsIds news identifiers for deletion from bd
     */
    public void deleteNews(Long[] newsIds) throws ServiceException;

    /**find news with author,tags and comments and create VO
     *@param pageNumber-current page number with particular news
     *@param newsOnPage- news number on the single page
     * @return DTO with List NewsVo objects and total news number
     */
    PaginationDTO findAllNewsOnPage(Integer pageNumber,int newsOnPage) throws ServiceException;

    /**find single news with author,tags and comments and create VO
     *
     * @param newsId-unique news identifier
     * @return  NewsVo object is news,author,tags, comments
     */

     News findNewsById(Long newsId) throws ServiceException;

    /**find news with author,tags and comments and create VO filtered by tags and author
     * @param newsFilter-object with filter data
     * @param pageNumber-current page number with particular news
     * @param newsOnPage- news number on the single page
     * @return object  with list NewsVo and total news number
     * @throws ServiceException
     */
    PaginationDTO findByAuthorAndTags(NewsFilter newsFilter,Integer pageNumber,int newsOnPage) throws ServiceException;
}
