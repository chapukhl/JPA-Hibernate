package com.epam.manage.service;

import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.impl.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ManageServiceTest {

    @InjectMocks
    private static NewsManagementServiceImpl manageService;

    @Mock
    private NewsAuthorServiceImpl newsAuthorService;

    @Mock
    private NewsTagServiceImpl newsTagService;

    @Mock
    private NewsServiceImpl newsService;

    @Mock
    private AuthorServiceImpl authorService;

    @Mock
    private TagServiceImpl tagService;

    @Mock
    private CommentServiceImpl commentService;

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
    public void testInsertNews() throws ServiceException {

        manageService.createNews(new News(), 10L, new Long[]{1L, 2L});
        verify(newsService).insert(any(News.class));
        verify(newsAuthorService).attachAuthorToNews(any(Long.class), any(Long.class));
        verify(newsTagService).attachTagsToNews(any(Long.class), any(Long[].class));
    }


    @Test
    public void testUpdateNews() throws ServiceException {

        manageService.updateNews(new News(), 10L, new Long[]{1L, 2L});
        verify(newsService).update(any(News.class));
        verify(newsAuthorService).detachAuthorToNews(any(Long.class));
        verify(newsTagService).detachTagsToNews(any(Long.class));
        verify(newsAuthorService).attachAuthorToNews(any(Long.class), any(Long.class));
        verify(newsTagService).attachTagsToNews(any(Long.class), any(Long[].class));

    }

    @Test
    public void testFindAllNewsOnPage() throws ServiceException {

        PaginationDTO result = mock(PaginationDTO.class);
        NewsManagementServiceImpl managementService = mock(NewsManagementServiceImpl.class);
        doReturn(result).when(managementService).findAllNewsOnPage(anyInt(), anyInt());


        doReturn(newsList).when(newsService).findAll(5, 8);
        doReturn(10).when(newsService).findTotalNewsNumber();
        manageService.findAllNewsOnPage(1, 4);

        verify(newsService, times(1)).findTotalNewsNumber();
        verify(newsService, times(1)).findAll(anyInt(),anyInt());
//        verify(commentService, times(3)).findCommentsByNewsId(any(Long.class));
//        verify(tagService, times(3)).findTagsByNewsId(any(Long.class));


    }


    @Test
    public void deleteNews() throws ServiceException {

        Long[] idsForDeletion = new Long[]{1L, 2L};
        manageService.deleteNews(idsForDeletion);
        verify(newsService).delete(idsForDeletion);



    }


    @Test
    public void testFindById() throws ServiceException {

        doReturn(newsList.get(0)).when(newsService).findById(1L);
        manageService.findNewsById(1L);
        verify(newsService).findById(1L);
//        verify(authorService, times(1)).findByNewsId(1L);
//        verify(commentService, times(1)).findCommentsByNewsId(1L);
//        verify(tagService, times(1)).findTagsByNewsId(1L);

    }


    @Test
    public void findByAuthorAndTags() throws ServiceException {


        List<News> newsVOList=new ArrayList<News>();
        for(int i=0;i<3;i++){
            newsVOList.add(new News());
        }

        PaginationDTO result = new PaginationDTO(newsVOList,5);
        doReturn(result).when(newsService).findFilteredNews(any(NewsFilter.class), anyInt(), anyInt());
        manageService.findByAuthorAndTags(any(NewsFilter.class), anyInt(),  anyInt());

        verify(newsService).findFilteredNews(any(NewsFilter.class), anyInt(), anyInt());
//        verify(authorService, times(3)).findByNewsId(anyLong());
//        verify(commentService, times(3)).findCommentsByNewsId(anyLong());
//        verify(tagService, times(3)).findTagsByNewsId(anyLong());


    }






}
