package com.epam.manage.service.impl;

import com.epam.manage.dao.INewsTagDao;
import com.epam.manage.service.IAuthorService;
import com.epam.manage.service.INewsService;
import com.epam.manage.service.INewsTagService;
import com.epam.manage.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsTagServiceImpl implements INewsTagService {

    @Autowired
    private INewsTagDao newsTagDao;



    @Override
    public void attachTagsToNews(Long newsId, Long[] tagIds){


        newsTagDao.attachTagsToNews(newsId, tagIds);


    }

    @Override
    public void detachTagsToNews(Long newsId) {

        newsTagDao.detachTagsToNews(newsId);

    }
}
