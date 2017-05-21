package com.epam.manage.dao.impl;

import com.epam.manage.dao.INewsDao;
import com.epam.manage.dao.INewsTagDao;
import com.epam.manage.dao.ITagDao;
import com.epam.manage.model.entity.News;
import com.epam.manage.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class})
public class NewsTagDaoImpl implements INewsTagDao {


    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    @Autowired
    private ITagDao tagDao;


    @Autowired
    private INewsDao newsDao;

    @Override
    public void attachTagsToNews(Long newsId, Long [] tagIds){


        News news= newsDao.findById(newsId);

        for(Long id:tagIds) {

            Tag tag = tagDao.findById(id);
            news.getTags().add(tag);
        }

        entityManager.merge(news);




    }


    @Override
    public void detachTagsToNews(Long newsId){

        List<Tag> tagList=new LinkedList<>();
        News news= newsDao.findById(newsId);
        news.setTags(tagList);

        entityManager.merge(news);





    }
}
