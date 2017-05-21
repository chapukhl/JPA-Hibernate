package com.epam.manage.dao;

import com.epam.manage.model.entity.Author;
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
@DatabaseSetup({"/dbunit/news.xml", "/dbunit/authors.xml"})

public class AuthorDaoTest {

    @Autowired
    //@Qualifier("jpaAuthorDao")
    private IAuthorDao authorDao;



    @Test
    public void testFindAllAuthors() throws Exception {

        List<Author> authorList = authorDao.findAll();

        Assert.assertEquals(10, authorList.size());

        Author actualAuthor = authorList.get(0);

        Author expectedAuthor = new Author();
        expectedAuthor.setName("Aleksandr");

        expectedAuthor.setAuthorId(2L);

        testAuthorEquals(expectedAuthor, actualAuthor);
    }


    @Test
    public void testUpdateAuthor() throws Exception {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("Luka");

        expectedAuthor.setAuthorId(1L);
        authorDao.update(expectedAuthor);
        Author actualAuthor = authorDao.findById(1L);

        testAuthorEquals(expectedAuthor, actualAuthor);
    }


    @Test
    public void testInsertAuthor() throws Exception {

        Author author = new Author();
        author.setName("Jack");

        Long authorId = authorDao.insert(author);
        Author actualAuthor = authorDao.findById(authorId);
        Assert.assertNotNull(actualAuthor);

        testAuthorsNameEquals(author, actualAuthor);

    }

    @Test
    public void testExpiredAuthor() throws Exception {

        authorDao.delete(2L);
        Assert.assertNotNull(authorDao.findById(2L));
        List<Author> activeAuthors = authorDao.findAllActive();
        boolean isExist = false;
        for (Author author : activeAuthors) {
            if (author.getAuthorId() == 2l) {
                isExist = true;
                break;
            }
        }

        Assert.assertFalse(isExist);

    }

    @Test
    public void findById() throws Exception {

        Author expectedAuthor = new Author();
        expectedAuthor.setName("Dmitriy");
        expectedAuthor.setAuthorId(1L);
        Author actualAuthor = authorDao.findById(1L);

        Assert.assertNotNull(expectedAuthor);
        Assert.assertNotNull(actualAuthor);
        testAuthorEquals(expectedAuthor, actualAuthor);
    }


//    @Test
//    public void findByNewsId() throws Exception {
//
//        Author expectedAuthor = new Author();
//        expectedAuthor.setName("Dmitriy");
//
//        expectedAuthor.setAuthorId(1L);
//        Author actualAuthor = authorDao.findByNewsId(2L);
//
//        Assert.assertNotNull(expectedAuthor);
//        Assert.assertNotNull(actualAuthor);
//        testAuthorEquals(expectedAuthor, actualAuthor);
//    }

    @Test
    public void testDeleteAuthor() throws Exception {

        authorDao.delete(2L);

        Author expiredAuthor=authorDao.findById(2L);

        Assert.assertNotNull(expiredAuthor);

        List<Author> activeAuthors = authorDao.findAllActive();
       Assert.assertFalse(activeAuthors.contains(expiredAuthor));

    }

    private void testAuthorEquals(Author expectedAuthor,
                                  Author actualAuthor) {

        Assert.assertEquals(expectedAuthor.getAuthorId(), actualAuthor.getAuthorId());
        Assert.assertEquals(expectedAuthor.getName(), actualAuthor.getName());

    }

    private void testAuthorsNameEquals(Author expectedAuthor,
                                       Author actualAuthor) {

        Assert.assertEquals(expectedAuthor.getName(), actualAuthor.getName());

    }
}
