package com.epam.manage.service;


import com.epam.manage.dao.impl.jpa.CommentDaoImpl;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Comment;
import com.epam.manage.service.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @InjectMocks
    private static CommentServiceImpl commentService;

    @Mock
    private CommentDaoImpl commentDao;

    private ArrayList<Comment> commentList;

    @Before
    public void setUp() {
        commentList = new ArrayList<Comment>() {
            {
                this.add(new Comment("Very interesting", Date.valueOf("2016-02-24")));
                this.add(new Comment("Bad news", Date.valueOf("2016-02-24")));
                this.add(new Comment("Fantastic!", Date.valueOf("2016-02-26")));
                this.add(new Comment("What are you think about it?", Date.valueOf("2016-02-26")));
                this.add(new Comment("It is really cool", Date.valueOf("2016-02-27")));
            }
        };
    }

    @Test
    public void testFindAllComments() throws DaoException, ServiceException {
        doReturn(commentList).when(commentDao).findAll();

        assertNotNull(commentService.findAll());
        assertEquals(5, commentService.findAll().size());
    }

    @Test
    public void testUpdateComment() throws DaoException, ServiceException {

        Comment comment = commentList.get(0);
        comment.setCommentText("Awesome");
        commentService.update(comment);
        verify(commentDao).update(any(Comment.class));
    }

    @Test
    public void testInsertComments() throws DaoException, ServiceException {
        doReturn(11L).when(commentDao).insert(any(Comment.class));
        Comment comment = commentList.get(1);
        Long commentId=commentService.insert(comment);

        verify(commentDao, times(1)).insert(Mockito.any(Comment.class));
        Assert.assertEquals(Long.valueOf(11),commentId);
    }

    @Test
    public void testDeleteComments() throws DaoException, ServiceException {
        commentService.delete(5l);
        verify(commentDao, times(1)).delete(Matchers.anyLong());
    }


    @Test
    public void findById() throws DaoException, ServiceException {

        Comment comment = new Comment("Cool!!", Date.valueOf("2016-02-24"));
        doReturn(comment).when(commentDao).findById(anyLong());
        Comment actualComment = commentService.findById(1L);
        assertNotNull(actualComment);
        assertEquals(comment.getCommentText(), actualComment.getCommentText());

        verify(commentDao).findById(1L);
    }


//    @Test
//    public void  findByNewsId() throws DaoException, ServiceException {
//
//
//
//        doReturn(commentList).when(commentDao).findCommentsByNewsId(anyLong());
//        List<Comment> actualComments= commentService.findCommentsByNewsId(1L);
//        assertNotNull(actualComments);
//        assertEquals(5, actualComments.size());
//        verify(commentDao).findCommentsByNewsId(1L);
//    }
}
