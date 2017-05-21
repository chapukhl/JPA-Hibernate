package com.epam.manage.dao.impl.jpa;

import com.epam.manage.dao.INewsDao;
import com.epam.manage.dao.INewsTagDao;
import com.epam.manage.dao.ITagDao;
import com.epam.manage.model.entity.News;
import com.epam.manage.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Profile("JPA")
@Transactional(rollbackFor = {Exception.class})
public class NewsTagDaoImpl implements INewsTagDao {


    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    @Autowired
    @Qualifier("jpaTagDao")
    private ITagDao tagDao;


    @Autowired
    @Qualifier("jpaNewsDao")
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

        Set<Tag> tagList=new HashSet<>();
        News news= newsDao.findById(newsId);
        news.setTags(tagList);

        entityManager.merge(news);





    }
}
