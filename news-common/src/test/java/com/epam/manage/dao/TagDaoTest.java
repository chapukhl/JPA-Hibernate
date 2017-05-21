package com.epam.manage.dao;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = "oracleConnection")
@DatabaseSetup("/dbunit/tags.xml")

public class TagDaoTest {


    @Autowired
  //  @Qualifier("jpaTagDao")
    private ITagDao tagDao;


    @Test
    public void testFindAllTags() throws Exception {

        List<Tag> newsList = tagDao.findAll();

        Assert.assertEquals(10, newsList.size());
    }


    @Test
    public void testUpdateTag() throws Exception {

        Tag expectedTag = new Tag();
        expectedTag.setTagId(3L);
        expectedTag.setTagName("politic");

        tagDao.update(expectedTag);

        Tag actualTag = tagDao.findById(1L);

        testTagEquals(expectedTag, actualTag);
    }


    @Test
    public void testInsertComment() throws Exception {

        Tag tag = new Tag();
        tag.setTagName("IT");
        Long tagId = tagDao.insert(tag);
        Tag actualTag = tagDao.findById(tagId);
        Assert.assertNotNull(actualTag);
        testTagEquals(tag, actualTag);

    }

    @Test
    public void testDeleteTag() throws Exception {
        tagDao.delete(2L);

        Assert.assertNull(tagDao.findById(2L));

    }

//    @Test
//    public void findByNewsId() throws Exception {
//
//        Tag expectedComment = tagDao.findById(5L);
//
//
//        List<Tag> actualCommentList = tagDao.findTagsByNewsId(5l);
//
//        Assert.assertEquals(1, actualCommentList.size());
//        Assert.assertEquals(expectedComment, actualCommentList.get(0));
//
//    }

    private void testTagEquals(Tag expectedTag,
                               Tag actualTag) {
        Assert.assertEquals(expectedTag.getTagName(), actualTag.getTagName());

    }
}
