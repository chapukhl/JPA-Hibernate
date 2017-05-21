package com.epam.manage.dao;

import com.epam.manage.model.entity.Author;
import com.epam.manage.model.entity.News;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.sql.Date;
import java.sql.Timestamp;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = "oracleConnection")
@DatabaseSetup({"/dbunit/news.xml", "/dbunit/authors.xml"})

public class NewsAuthorDaoTest {


    @Autowired
   // @Qualifier("jpaNewsDao")
    private INewsDao newsDao;


    @Autowired
    //@Qualifier("jpaNewsAuthorDao")
    private INewsAuthorDao newsAuthorDao;


    @Test
    public void testAttachAuthorToNews() throws Exception {

        News news = newsDao.findById(4L);


        Assert.assertEquals(0, news.getAuthors().size());

        newsAuthorDao.attachAuthorToNews(4L, 2L);
        news = newsDao.findById(4L);

        Long expectedId = 2L;
        String expectedName = "Aleksandr";
        Author expectedAuthor=new Author();
        expectedAuthor.setName(expectedName);
        expectedAuthor.setAuthorId(expectedId);
       expectedAuthor.setExpired(Timestamp.valueOf("2016-05-10 00:00:00.0"));
        System.out.println(news.getAuthors());
        Assert.assertTrue(news.getAuthors().contains(expectedAuthor));

    }


    @Test
    public void testDetachAuthorToNews() throws Exception {

        News news = newsDao.findById(2L);
        Author author = (Author)(news.getAuthors().toArray())[0];
        Assert.assertNotNull(author);
        Long detachedNewsId = 2L;

        newsAuthorDao.detachAuthorToNews(detachedNewsId);
        news = newsDao.findById(2L);
        Assert.assertEquals(0, news.getAuthors().size());


    }


}
