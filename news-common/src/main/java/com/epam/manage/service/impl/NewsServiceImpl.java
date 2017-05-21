package com.epam.manage.service.impl;

import com.epam.manage.dao.INewsDao;
import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements INewsService {

    @Autowired
    private INewsDao newsDao;

    @Override
    public List<News> findAll(int startRecordNum,int endRecordNum)  {


            return newsDao.findAll(startRecordNum,endRecordNum);

    }

    @Override
    public News findById(Long id) {


            return newsDao.findById(id);

    }

    @Override
    public void update(News entity) throws ServiceException {


        try {
            newsDao.update(entity);
        } catch (DaoException e) {
           throw new ServiceException(e);
        }

    }

    @Override
    public void delete(Long[] newsId)  {

            newsDao.delete(newsId);

    }

    @Override
    public Long insert(News entity) {


            return newsDao.insert(entity);

    }



    public int findTotalNewsNumber()  {

            return newsDao.findTotalNewsNumber();

    }

    @Override
    public PaginationDTO findFilteredNews(NewsFilter newsFilter, int startNewsNumber, int endNewsNumber)  {


            return newsDao.findFilteredNews(newsFilter,startNewsNumber,endNewsNumber);

    }



    public Long findPreviousNewsId(Long id)  {

            return newsDao.findPreviousNewsId(id);

    }

    public Long findNextNewsId(Long id) {

            return newsDao.findNextNewsId(id);

    }

    @Override
    public Long findPreviousFilteredNewsId(NewsFilter newsFilter, Long id)  {

            return newsDao.findPreviousFilteredNewsId(newsFilter,id);

    }

    @Override
    public Long findNextFilteredNewsId(NewsFilter newsFilter, Long id) {

            return newsDao.findNextFilteredNewsId(newsFilter,id);
    }
}
