package com.epam.manage.service;

import com.epam.manage.dao.impl.jpa.AuthorDaoImpl;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Author;
import com.epam.manage.service.impl.AuthorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @InjectMocks
    private static AuthorServiceImpl authorService;

    @Mock
    private AuthorDaoImpl authorDao;

    private ArrayList<Author> authorList;

    @Before
    public void setUp() {
        authorList = new ArrayList<Author>() {
            {
                this.add(new Author("Dmitriy"));
                this.add(new Author("Aleksandr"));
                this.add(new Author("Vitaliy"));
                this.add(new Author("Tatyana"));
                this.add(new Author("Victor"));
            }
        };
    }

    @Test
    public void testFindAllAuthors() throws DaoException, ServiceException {

        doReturn(authorList).when(authorDao).findAll();
        List<Author> list = authorService.findAll();
        assertNotNull(list);
        assertEquals(5, list.size());

        verify(authorDao).findAll();
    }



    @Test
    public void testFindAllActive() throws DaoException, ServiceException {

        doReturn(authorList).when(authorDao).findAllActive();
        List<Author> list = authorService.findAllActive();
        assertNotNull(list);
        assertEquals(5, list.size());

        verify(authorDao).findAllActive();
    }



    @Test
    public void testUpdateAuthor() throws DaoException, ServiceException {

        Author author = authorList.get(0);
        author.setName("Lev");
        authorService.update(author);
        verify(authorDao).update(any(Author.class));
    }



    @Test
    public void testInsertAuthor() throws DaoException, ServiceException {

        doReturn(11L).when(authorDao).insert(any(Author.class));
        Author author = authorList.get(1);
        Long authorId = authorService.insert(author);
        verify(authorDao, times(1)).insert(Mockito.any(Author.class));
        Assert.assertEquals(Long.valueOf(11), authorId);
    }



    @Test
    public void testDeleteAuthor() throws DaoException, ServiceException {

        authorService.delete(5L);

        verify(authorDao, times(1)).delete(Matchers.anyLong());
    }



    @Test
    public void testFindById() throws DaoException, ServiceException {

        Author author = new Author("Luka");
        doReturn(author).when(authorDao).findById(anyLong());
        Author actualAuthor = authorService.findById(1L);
        assertNotNull(actualAuthor);
        assertEquals(author.getName(), actualAuthor.getName());

        verify(authorDao).findById(1L);
    }



//    @Test
//    public void  testFindByNewsId() throws DaoException, ServiceException {
//
//        Author author = new Author("Luka");
//
//        doReturn(author).when(authorDao).findByNewsId(anyLong());
//        Author actualAuthor = authorService.findByNewsId(1L);
//        assertNotNull(actualAuthor);
//        assertEquals(author.getName(), actualAuthor.getName());
//
//        verify(authorDao).findByNewsId(1L);
//    }
//
//    @Test(expected=ServiceException.class)
//    public void  testFindByNewsIdException() throws DaoException, ServiceException {
//
//      doThrow(DaoException.class).when(authorDao).findByNewsId(anyLong());
//       authorService.findByNewsId(1L);
//
//    }



}
