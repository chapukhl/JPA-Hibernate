package com.epam.manage.dao.impl.hibernate;

import com.epam.manage.dao.IAuthorDao;
import com.epam.manage.dao.INewsAuthorDao;
import com.epam.manage.dao.INewsDao;
import com.epam.manage.model.entity.Author;
import com.epam.manage.model.entity.News;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;


@Profile("Hibernate")
@Transactional(rollbackFor = {Exception.class})
public class NewsAuthorDaoImpl implements INewsAuthorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("hibernateAuthorDao")
    private IAuthorDao authorDao;

    @Autowired
    @Qualifier("hibernateNewsDao")
    private INewsDao newsDao;

    @Override
    public void attachAuthorToNews(Long newsId, Long authorId){


        News news= newsDao.findById(newsId);
        Author author= authorDao.findById(authorId);
        news.getAuthors().add(author);
        sessionFactory.getCurrentSession().merge(news);





    }

    @Override
    public void detachAuthorToNews(Long newsId){

        List<Author> authorList=new LinkedList<>();
        News news= newsDao.findById(newsId);
        news.setAuthors(authorList);
        sessionFactory.getCurrentSession().merge(news);


    }
}
