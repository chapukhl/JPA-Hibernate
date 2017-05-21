package com.epam.manage.service.impl;

import com.epam.manage.dao.INewsAuthorDao;
import com.epam.manage.service.IAuthorService;
import com.epam.manage.service.INewsAuthorService;
import com.epam.manage.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsAuthorServiceImpl implements INewsAuthorService {

    @Autowired
    private INewsAuthorDao newsAuthorDao;

    @Override
    public void attachAuthorToNews(Long newsId, Long authorId)  {

        newsAuthorDao.attachAuthorToNews(newsId, authorId);

    }

    @Override
    public void detachAuthorToNews(Long newsId) {

        newsAuthorDao.detachAuthorToNews(newsId);

    }



}
