package com.epam.manage.dao.impl.jpa;

import com.epam.manage.dao.IAuthorDao;
import com.epam.manage.dao.INewsAuthorDao;
import com.epam.manage.dao.INewsDao;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Author;
import com.epam.manage.model.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.LinkedList;
import java.util.List;


@Profile("JPA")
@Transactional(rollbackFor = {Exception.class})
public class NewsAuthorDaoImpl implements INewsAuthorDao {

    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    @Autowired
    @Qualifier("jpaAuthorDao")
    private IAuthorDao authorDao;

    @Autowired
    @Qualifier("jpaNewsDao")
    private INewsDao newsDao;

    @Override
    public void attachAuthorToNews(Long newsId, Long authorId){


        News news= newsDao.findById(newsId);
        Author author= authorDao.findById(authorId);

            news.getAuthors().add(author);


        entityManager.merge(news);




    }

    @Override
    public void detachAuthorToNews(Long newsId){

        List<Author> authorList=new LinkedList<>();
        News news= newsDao.findById(newsId);
        news.setAuthors(authorList);

        entityManager.merge(news);

    }
}
