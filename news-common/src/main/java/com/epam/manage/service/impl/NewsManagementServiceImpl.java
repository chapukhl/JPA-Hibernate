package com.epam.manage.service.impl;

import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.Author;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(rollbackFor = {ServiceException.class,RuntimeException.class})
public class NewsManagementServiceImpl implements IManageService {


    @Autowired
    private INewsService newsService;

    @Autowired
    private INewsAuthorService newsAuthorService;

    @Autowired
    private INewsTagService newsTagService;

    @Override
    public void createNews(News news, Long authorId, Long[] tagIds) throws ServiceException {
        try {


            if (authorId == null) {
                throw new ServiceException("Author is not exist");
            }
            Long newsId = newsService.insert(news);
            newsAuthorService.attachAuthorToNews(newsId, authorId);
            if (tagIds != null && tagIds.length != 0) {
                newsTagService.attachTagsToNews(newsId, tagIds);
            }


        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void updateNews(News news, Long authorId, Long[] tagIds) throws ServiceException {
        try {

            newsService.update(news);

            if (authorId == null) {
                throw new ServiceException("Author is not exist");
            }
            else {
                if (tagIds != null && tagIds.length != 0) {

                    newsAuthorService.detachAuthorToNews(news.getNewsId());
                    newsTagService.detachTagsToNews(news.getNewsId());
                    newsAuthorService.attachAuthorToNews(news.getNewsId(), authorId);
                    newsTagService.attachTagsToNews(news.getNewsId(), tagIds);
                }
                else{
                        newsService.update(news);
                        newsAuthorService.detachAuthorToNews(news.getNewsId());
                        newsAuthorService.attachAuthorToNews(news.getNewsId(), authorId);
                }
            }

        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteNews(Long[] newsIds){


            if(newsIds!=null && newsIds.length!=0) {


//                for(Long id:newsIds) {
//                    newsAuthorService.detachAuthorToNews(id);
//                    newsTagService.detachTagsToNews(id);
//                }
                newsService.delete(newsIds);
            }

    }

    @Override
    public PaginationDTO findAllNewsOnPage(Integer pageNumber,int newsOnPage) throws ServiceException {

        List<News> newsList = new ArrayList<News>();
        int totalNewsNumber;



            totalNewsNumber = newsService.findTotalNewsNumber();

            if (pageNumber == null || pageNumber < 0) {
                pageNumber = 0;
            }

            newsList = newsService.findAll(getStartNewsNumber(pageNumber,newsOnPage), getEndNewsNumber(pageNumber,newsOnPage));


        return new PaginationDTO(newsList, totalNewsNumber);
    }


    @Override
    public PaginationDTO findByAuthorAndTags(NewsFilter newsFilter, Integer pageNumber,int newsOnPage) throws ServiceException {

        PaginationDTO paginationDTO;


            if (pageNumber == null || pageNumber < 0) {
                pageNumber = 0;
            }

           paginationDTO=newsService.findFilteredNews(newsFilter,getStartNewsNumber(pageNumber,newsOnPage), getEndNewsNumber(pageNumber,newsOnPage));



        return paginationDTO;
    }


    @Override
    public News findNewsById(Long newsId) throws ServiceException {
        News news;


             news = newsService.findById(newsId);



        return news;
    }

    private int getEndNewsNumber(int pageNumber,int newsOnPage) {
        return pageNumber * newsOnPage + newsOnPage;
    }

    private int getStartNewsNumber(int pageNumber,int newsOnPage) {
        return pageNumber * newsOnPage + 1;
    }


}
