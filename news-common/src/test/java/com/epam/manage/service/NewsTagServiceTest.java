package com.epam.manage.service;

import com.epam.manage.dao.impl.jpa.NewsTagDaoImpl;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.service.impl.NewsTagServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NewsTagServiceTest {

    @InjectMocks
    private static NewsTagServiceImpl newsTagService;

    @Mock
    private NewsTagDaoImpl newsTagDao;



    @Test
    public void testAttachTagsToNews() throws ServiceException, DaoException {

        newsTagService.attachTagsToNews(anyLong(),any(Long[].class));
        verify(newsTagDao).attachTagsToNews(anyLong(),any(Long[].class));

    }

    @Test
    public void testDetachTagsToNews() throws ServiceException, DaoException {

        newsTagService.detachTagsToNews(anyLong());
        verify(newsTagDao).detachTagsToNews(anyLong());

    }
}
