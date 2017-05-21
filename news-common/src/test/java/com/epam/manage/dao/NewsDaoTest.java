package com.epam.manage.dao;


import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.model.dto.PaginationDTO;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, TransactionalTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection = "oracleConnection")
@DatabaseSetup({"/dbunit/news.xml", "/dbunit/authors.xml", "/dbunit/tags.xml"})

public class NewsDaoTest {

    @Autowired
   // @Qualifier("jpaNewsDao")
    private INewsDao newsDao;


    @Test
    public void testFindAllNews() throws Exception {

        List<News> newsList = newsDao.findAll(1, 5);

        Assert.assertEquals(5, newsList.size());
    }

    @Test
    public void testFindTotalNewsNumber() throws Exception{

        int actualValue=newsDao.findTotalNewsNumber();
        Assert.assertEquals(10,actualValue);

    }

    @Test
    public void testFindNewsById() throws Exception {

        News expectedNews = newsDao.findById(2L);
        News actualNews = new News(null, "Financial crisis", "enterprises and factories are closed", "crisis",
                Date.valueOf("2016-03-04"), Date.valueOf("2016-03-15"));

        testNewsEquals(actualNews, expectedNews);
    }

    @Test
    public void testInsertNews() throws Exception {

        News expectedNews = new News(null, "New virus",
                "New dangerous virus", "Virus",
                Date.valueOf("2016-12-13"), Date.valueOf("2016-12-13"));
         Long newsId = newsDao.insert(expectedNews);
//       List<News> newsList = newsDao.findAll(1, 12);
        News news=newsDao.findById(newsId);
        testNewsEquals(news,expectedNews);
    }





    @Test
    public void testFindFilteredNewsByAuthorAndTags() throws Exception {

        NewsFilter newsFilter = new NewsFilter(7L, new Long[]{1L, 6L, 8L});
        PaginationDTO paginationDTO = newsDao.findFilteredNews(newsFilter, 1, 4);
        Assert.assertEquals(3, paginationDTO.getTotalNews());
        List<News> expectedList = generateForFilterByAuthorAndTag();
        List<News> actualList = paginationDTO.getNewsList();
        testNewsVOListEquals(expectedList, actualList);


    }

    @Test
    public void testFindFilteredNewsByTags() throws Exception {

        NewsFilter newsFilter = new NewsFilter(null, new Long[]{1L, 6L, 8L});
        PaginationDTO paginationDTO = newsDao.findFilteredNews(newsFilter, 1, 4);
        Assert.assertEquals(4, paginationDTO.getTotalNews());
        List<News> expectedList = generateForFilterByAuthor();
        List<News> actualList = paginationDTO.getNewsList();
        testNewsVOListEquals(expectedList, actualList);

    }

    @Test
    public void testFindFilteredNewsByAuthor() throws Exception {

        NewsFilter newsFilter = new NewsFilter(7L, null);
        PaginationDTO paginationDTO = newsDao.findFilteredNews(newsFilter, 1, 4);
        Assert.assertEquals(3, paginationDTO.getTotalNews());
        List<News> expectedList = generateForFilterByAuthorAndTag();
        List<News> actualList = paginationDTO.getNewsList();
        testNewsVOListEquals(expectedList, actualList);
    }

    @Test
    public void testFindPreviousNewsId() throws Exception {

        long prevId = newsDao.findPreviousNewsId(5L);
        Assert.assertEquals(4L, prevId);

    }


    @Test
    public void testFindNextNewsId() throws Exception {

        long nextId = newsDao.findNextNewsId(5L);
        Assert.assertEquals(6L, nextId);

    }

    @Test
    public void testFindPreviousFilteredNewsIdByAuthorAndTags() throws Exception {

        NewsFilter newsFilter = new NewsFilter(7L, new Long[]{1L, 6L, 8L});
        long prevId = newsDao.findPreviousFilteredNewsId(newsFilter, 9L);
        Assert.assertEquals(10L, prevId);


    }

    @Test
    public void testFindPreviousFilteredNewsIdByTags() throws Exception {

        NewsFilter newsFilter = new NewsFilter(null, new Long[]{1L, 6L, 8L});
        long prevId = newsDao.findPreviousFilteredNewsId(newsFilter, 9L);
        Assert.assertEquals(10L, prevId);

    }

    @Test
    public void testFindPreviousFilteredNewsIdByAuthor() throws Exception {

        NewsFilter newsFilter = new NewsFilter(7L, null);
        long prevId = newsDao.findPreviousFilteredNewsId(newsFilter, 9L);
        Assert.assertEquals(10L, prevId);

    }

    @Test
    public void testFindNextFilteredNewsIdByAuthorAndTags() throws Exception {

        NewsFilter newsFilter = new NewsFilter(7L, new Long[]{1L, 6L, 8L});
        long nextId = newsDao.findNextFilteredNewsId(newsFilter, 9L);
        Assert.assertEquals(8L, nextId);

    }

    @Test
    public void testFindNextFilteredNewsIdByTags() throws Exception {

        NewsFilter newsFilter = new NewsFilter(null, new Long[]{1L, 6L, 8L});
        long nextId = newsDao.findNextFilteredNewsId(newsFilter, 9L);
        Assert.assertEquals(2L, nextId);
    }

    @Test
    public void testFindNextFilteredNewsIdByAuthor() throws Exception {

        NewsFilter newsFilter = new NewsFilter(7L, null);
        long nextId  = newsDao.findNextFilteredNewsId(newsFilter, 9L);
        Assert.assertEquals(8L, nextId);
    }

    @Test
    public void testUpdateNews() throws Exception {

        News expectedNews = new News(null,
                "Crisis is canceled",
                "Financial Crisis is canceled", "crisis",
                Date.valueOf("2016-03-12"), Date.valueOf("2016-03-12"));
        expectedNews.setNewsId(2l);
        newsDao.update(expectedNews);
        News actualNews = newsDao.findById(2L);

        testNewsEquals(expectedNews, actualNews);
    }


    @Test
    public void testDeleteNews() throws Exception {

        Long[] newsId = {1L, 4L};
        newsDao.delete(newsId);

        Assert.assertNull(newsDao.findById(1L));
        Assert.assertNull(newsDao.findById(4L));


    }

    private void testNewsListEquals(List<News> expectedList,
                                    List<News> actualList) throws Exception {

        int actualSize = actualList.size();
        int expectedSize = expectedList.size();
        Assert.assertEquals(expectedSize, actualSize);

        for (int i = 0; i < expectedSize; i++) {
            News expectedNews = expectedList.get(i);
            News actualNews = actualList.get(i);
            testNewsEquals(expectedNews, actualNews);
        }
    }

    private void testNewsVOListEquals(List<News> expectedList,
                                      List<News> actualList) throws Exception {

        int actualSize = actualList.size();
        int expectedSize = expectedList.size();
        Assert.assertEquals(expectedSize, actualSize);

        for (int i = 0; i < expectedSize; i++) {
            News expectedNews = expectedList.get(i);
            News actualNews = actualList.get(i);
            testNewsEquals(expectedNews, actualNews);
        }
    }

    private void testNewsEquals(News expectedNews,
                                News actualNews) throws Exception {

        Assert.assertEquals(expectedNews.getTitle(), actualNews.getTitle());
        Assert.assertEquals(expectedNews.getShortText(),
                actualNews.getShortText());
        Assert.assertEquals(expectedNews.getFullText(),
                actualNews.getFullText());
        Assert.assertEquals(expectedNews.getCreationDate().getTime(),
                actualNews.getCreationDate().getTime());
        Assert.assertEquals(expectedNews.getModificationDate().getTime(),
                actualNews.getModificationDate().getTime());
    }


    private List<News> generateForFilterByAuthor() {
        return new ArrayList<News>() {
            {

                this.add(new News(null,
                        "Game Wot ",
                        "Game Wot conquered the world", "Popular Game",
                        Date.valueOf("2016-07-03"), Date.valueOf("2016-07-03")));
                this.add(new News(null, "Financial crisis",
                        "enterprises and factories are closed", "crisis", Date
                        .valueOf("2016-03-04"), Date
                        .valueOf("2016-03-15")));
                this.add(new News(null,
                        "Programmers' salaries",
                        "Programmers' salaries have risen sharply", "Salary",
                        Date.valueOf("2016-07-20"), Date.valueOf("2015-07-20")));
                this.add(new News(null, "Global warming",
                        "Global warming is not a threat", "Global warming", Date
                        .valueOf("2015-02-16"), Date
                        .valueOf("2015-02-16")));


            }
        };
    }

    private List<News> generateForFilterByAuthorAndTag() {
        return new ArrayList<News>() {
            {
                this.add(new News(null,
                        "Game Wot ",
                        "Game Wot conquered the world", "Popular Game",
                        Date.valueOf("2016-07-03"), Date.valueOf("2016-07-03")));
                this.add(new News(null,
                        "Programmers' salaries",
                        "Programmers' salaries have risen sharply", "Salary",
                        Date.valueOf("2016-07-20"), Date.valueOf("2015-07-20")));
                this.add(new News(null, "Global warming",
                        "Global warming is not a threat", "Global warming", Date
                        .valueOf("2015-02-16"), Date
                        .valueOf("2015-02-16")));

            }
        };
    }


}
