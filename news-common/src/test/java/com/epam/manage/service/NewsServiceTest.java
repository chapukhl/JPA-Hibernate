package com.epam.manage.service;


import com.epam.manage.dao.impl.jpa.NewsDaoImpl;
import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.impl.NewsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    @InjectMocks
    private static NewsServiceImpl newsService;

    @Mock
    private NewsDaoImpl newsDao;

    private ArrayList<News> newsList;

    @Before
    public void setUp() {
        newsList = new ArrayList<News>() {
            {
                this.add(new News(1L,
                        "New champion",
                        "New champion is already known", "chess",
                        new Date(1000000000), new Date(1100000000)));
                this.add(new News(2L, "Financial crisis",
                        "enterprises and factories are closed", "crisis",
                        new Date(2000000000), new Date(2100000000)));
                this.add(new News(3L,
                        "Trade union",
                        "Created a new trade union", "European Union",
                        new Date(2111000000), new Date(2120000000)));
            }


        };
    }

    @Test
    public void testFindAllNews() throws ServiceException, DaoException {
        doReturn(newsList).when(newsDao).findAll(1,3);

        assertNotNull(newsService.findAll(1,3));
        assertEquals(3, newsService.findAll(1,3).size());
    }

    @Test
    public void testFindNewsById() throws ServiceException, DaoException {

        News news1 = newsList.get(0);
        doReturn(news1).when(newsDao).findById(1L);
        News news2 = newsList.get(2);
        doReturn(news2).when(newsDao).findById(3L);

        assertEquals(news1, newsService.findById(1L));
        assertEquals(news2, newsService.findById(3L));
        assertNull(newsService.findById(2L));
    }




    @Test
    public void testInsertNews() throws ServiceException, DaoException {


        doReturn(11L).when(newsDao).insert(any(News.class));
        verifyZeroInteractions(newsDao);

        News news = newsList.get(0);

        Long newsId = newsService.insert(news);

        verify(newsDao, times(1)).insert(news);
        Assert.assertEquals(Long.valueOf(11),newsId);
    }

    @Test
    public void testDeleteNews() throws ServiceException, DaoException {

        Long[] newsId1 = {5L, 6L};
        newsService.delete(newsId1);


        verify(newsDao, times(1)).delete(any(Long[].class));
    }

    @Test
    public void testUpdateNews() throws ServiceException, DaoException {

        News news = newsList.get(1);
        news.setTitle("War");
        newsService.update(news);
        verify(newsDao).update(any(News.class));


    }

    @Test
    public void  testFindTotalNewsNumber() throws ServiceException, DaoException {

        doReturn(10).when(newsDao).findTotalNewsNumber();
        int totalNumber=newsService.findTotalNewsNumber();
        Assert.assertEquals(10,totalNumber);
        verify(newsDao).findTotalNewsNumber();

    }

    @Test
    public void  testFindFilteredNews() throws ServiceException, DaoException {

        newsService.findFilteredNews(any(NewsFilter.class),anyInt(),anyInt());
        verify(newsDao).findFilteredNews(any(NewsFilter.class),anyInt(),anyInt());

    }



    @Test
    public void  testFindPreviousNewsId() throws ServiceException, DaoException {

        newsService.findPreviousNewsId(anyLong());
        verify(newsDao).findPreviousNewsId(anyLong());

    }

    @Test
    public void  testFindNextNewsId() throws ServiceException, DaoException {

        newsService.findNextNewsId(anyLong());
        verify(newsDao).findNextNewsId(anyLong());
    }

    @Test
    public void findPreviousFilteredNewsId() throws ServiceException, DaoException {

        newsService.findPreviousFilteredNewsId(any(NewsFilter.class),anyLong());
        verify(newsDao).findPreviousFilteredNewsId(any(NewsFilter.class),anyLong());
    }



    @Test
    public void findNextFilteredNewsId() throws ServiceException, DaoException {

        newsService.findNextFilteredNewsId(any(NewsFilter.class),anyLong());
        verify(newsDao).findNextFilteredNewsId(any(NewsFilter.class),anyLong());
    }

}
