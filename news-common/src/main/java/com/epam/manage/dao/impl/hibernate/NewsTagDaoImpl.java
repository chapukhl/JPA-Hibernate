package com.epam.manage.dao.impl.hibernate;

import com.epam.manage.dao.INewsDao;
import com.epam.manage.dao.INewsTagDao;
import com.epam.manage.dao.ITagDao;
import com.epam.manage.model.entity.News;
import com.epam.manage.model.entity.Tag;
import com.epam.manage.service.INewsService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Profile("Hibernate")
@Transactional(rollbackFor = {Exception.class})
public class NewsTagDaoImpl implements INewsTagDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("hibernateTagDao")
    private ITagDao tagDao;

    @Autowired
    @Qualifier("hibernateNewsDao")
    private INewsDao newsDao;

    @Override
    public void attachTagsToNews(Long newsId, Long [] tagIds){


        News news=newsDao.findById(newsId);

        for(Long id:tagIds) {

            Tag tag = tagDao.findById(id);
            news.getTags().add(tag);
        }

        sessionFactory.getCurrentSession().merge(news);




    }


    @Override
    public void detachTagsToNews(Long newsId){

        Set<Tag> tags=new HashSet<>();
        News news=newsDao.findById(newsId);
        news.setTags(tags);
        sessionFactory.getCurrentSession().merge(news);


    }
}
