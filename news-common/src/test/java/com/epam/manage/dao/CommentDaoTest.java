package com.epam.manage.dao;


import com.epam.manage.model.entity.Comment;
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
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = "oracleConnection")
@DatabaseSetup("/dbunit/comments.xml")
public class CommentDaoTest {


    @Autowired
    //@Qualifier("jpaCommentDao")
    private ICommentDao commentDao;

    @Test
    public void testFindAllComments() throws Exception {

        List<Comment> newsList = commentDao.findAll();
        System.out.println(newsList);
        Assert.assertEquals(10, newsList.size());
        Comment actualComment=newsList.get(0);
        Comment expectedComment=new Comment();
        expectedComment.setCommentText("It is really cool");
        expectedComment.setCreationDate(Date.valueOf("2016-02-27"));
        testCommentEquals(expectedComment, actualComment);

    }


    @Test
    public void testUpdateComment() throws Exception {

        Comment expectedComment = new Comment();
        System.out.println(commentDao.findById(1L));
        expectedComment.setCommentText("Very interesting");
        expectedComment.setCreationDate(Date.valueOf("2016-02-24"));
        expectedComment.setCommentId(1L);
        News news=new News();
        news.setNewsId(2L);
        expectedComment.setNews(news);
        commentDao.update(expectedComment);
        Comment actualComment = commentDao.findById(1L);

        testCommentEquals(expectedComment, actualComment);
    }


    @Test
    public void testInsertComment() throws Exception {

        Comment comment = new Comment();

        comment.setCommentText("it's surprise");
        comment.setCreationDate(Date.valueOf("2014-03-21"));
        News news=new News();
        news.setNewsId(1L);
        comment.setNews(news);

        Long commentId=commentDao.insert(comment);
        Comment actualComment=commentDao.findById(commentId);

        Assert.assertNotNull(actualComment);
        testCommentEquals(comment,actualComment);

    }

    @Test
    public void testDeleteComment() throws Exception {

        commentDao.delete(2L);

        Assert.assertNull(commentDao.findById(2L));

    }

//    @Test
//    public void findByNewsId() throws Exception {
//
//        Comment expectedComment1 = commentDao.findById(1L);
//        Comment expectedComment2 = commentDao.findById(2L);
//        List<Comment> actualCommentList = commentDao.findCommentsByNewsId(1L);
//        Collections.sort(actualCommentList);
//        Assert.assertEquals(2, actualCommentList.size());
//        Assert.assertEquals(expectedComment1, actualCommentList.get(1));
//        Assert.assertEquals(expectedComment2, actualCommentList.get(0));
//    }

    private void testCommentEquals(Comment expectedComment,
                                   Comment actualComment) {

        Assert.assertEquals(expectedComment.getCommentText(), actualComment.getCommentText());
        Assert.assertEquals(expectedComment.getCreationDate(),
                actualComment.getCreationDate());
    }
}
