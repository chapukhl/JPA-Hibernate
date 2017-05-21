package com.epam.manage.service;

import com.epam.manage.dao.impl.jpa.NewsAuthorDaoImpl;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.service.impl.NewsAuthorServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsAuthorServiceTest {

    @InjectMocks
    private static NewsAuthorServiceImpl newsAuthorService;

    @Mock
    private NewsAuthorDaoImpl newsAuthorDao;

    @Test
    public void testAttachAuthorToNews() throws ServiceException, DaoException {

        newsAuthorService.attachAuthorToNews(anyLong(),anyLong());
        verify(newsAuthorDao).attachAuthorToNews(anyLong(), anyLong());

    }

    @Test
    public void testDetachAuthorToNews() throws ServiceException, DaoException {

        newsAuthorService.detachAuthorToNews(anyLong());
        verify(newsAuthorDao).detachAuthorToNews(anyLong());

    }
}
