package com.epam.manage.service;


import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.News;

import java.util.List;


public interface INewsService {


    /**Find All news in db and return list of news objects
     * @param startRecordNum-start number of news record on page
     * @param endRecordNum-end number of news record on page
     * @return full list of news in appropriate table in database
     */
    List<News> findAll(int startRecordNum,int endRecordNum);

    /**find news in bd by particular id
     * @param id - unique news identifier
     * @return -news from table if id exists, else null
     */
    News findById(Long id) ;

    /**delete news row from db with particular id
     * @param newsId-unique news identifiers
     */
    void delete(Long[] newsId) ;

    /**insert row in bd appropriate news object in parameter
     * @param news-object to insertion in database
     */
    Long insert(News news) throws ServiceException;

    /**update existing news's row in bd
     * @param news-object to update in database
     */
    void update(News news) throws ServiceException;




    /**counting news in database
     *
     * @return news number
     */
    public int findTotalNewsNumber() ;

    /**
     *
     * @param newsFilter-object for news filtering
     * @param startNewsNumber-start number of news record on page
     * @param endNewsNumber-end number of news record on page
     * @return- object  with list NewsVo and total news number
     */
    public PaginationDTO findFilteredNews(NewsFilter newsFilter,int startNewsNumber,int endNewsNumber);

    /**
     *
     * @param id-current news id
     * @return- previous news identifier
     */
    public Long findPreviousNewsId(Long id);


    /**
     *
     * @param id-current news id
     * @return- next news identifier
     */
    public Long findNextNewsId(Long id) ;

    /**
     * @param newsFilter-object for news filtering
     * @param id-current news id
     * @return- previous news identifier after current id  among filtered news
     */
    public Long findPreviousFilteredNewsId(NewsFilter newsFilter,Long id) ;


    /**
     * @param newsFilter-object for news filtering
     * @param id-current news id
     * @return- next news identifier after current id  among filtered news
     */
    public Long findNextFilteredNewsId(NewsFilter newsFilter,Long id) ;



}