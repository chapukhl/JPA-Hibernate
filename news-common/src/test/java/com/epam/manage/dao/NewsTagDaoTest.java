package com.epam.manage.dao;

import com.epam.manage.model.entity.News;
import com.epam.manage.model.entity.Tag;
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

import java.util.List;
import java.util.Set;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = "oracleConnection")
@DatabaseSetup({"/dbunit/news.xml", "/dbunit/tags.xml"})
public class NewsTagDaoTest {

    @Autowired
    private INewsDao newsDao;


    @Autowired
    private INewsTagDao newsTagDao;

    @Autowired
    private ITagDao tagDao;

    @Test
    public void   testAttachTagsToNews() throws Exception {


        News news=newsDao.findById(9L);
        Set<Tag> tagList=news.getTags();
        Assert.assertEquals(1, tagList.size());
        newsTagDao.attachTagsToNews(9L, new Long[]{2L, 3L});

        news=newsDao.findById(9L);
        tagList=news.getTags();
        Assert.assertEquals(3,tagList.size());
        Tag tag=tagDao.findById(2L);
        Assert.assertTrue(tagList.contains(tag));


    }

    @Test
    public void   testDetachTagsToNews() throws Exception {

        News news=newsDao.findById(8L);
        Set<Tag> tagList=news.getTags();
        Assert.assertEquals(1, tagList.size());
        newsTagDao.detachTagsToNews(8L);
        news=newsDao.findById(8L);
        tagList=news.getTags();
        Assert.assertEquals(0,tagList.size());


    }

}
