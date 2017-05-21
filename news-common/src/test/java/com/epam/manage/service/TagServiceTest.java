package com.epam.manage.service;

import com.epam.manage.dao.impl.jpa.TagDaoImpl;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Tag;
import com.epam.manage.service.impl.TagServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {


    @InjectMocks
    private static TagServiceImpl tagService;

    @Mock
    private TagDaoImpl tagDao;

    private ArrayList<Tag> tagList;

    @Before
    public void setUp() {
        tagList = new ArrayList<Tag>() {
            {
                this.add(new Tag("politic"));
                this.add(new Tag("science"));
                this.add(new Tag("entertainment"));
                this.add(new Tag("economy"));
                this.add(new Tag("culture"));
            }
        };
    }

    @Test
    public void testFindAllTags() throws DaoException, ServiceException {
        doReturn(tagList).when(tagDao).findAll();

        assertNotNull(tagService.findAll());
        assertEquals(5, tagService.findAll().size());
    }

    @Test
    public void testUpdateTag() throws DaoException, ServiceException {

        Tag tag = tagList.get(0);
        tag.setTagName("Books");
        tagService.update(tag);
        verify(tagDao).update(any(Tag.class));
    }

    @Test
    public void testInsertTag() throws DaoException, ServiceException {
        doReturn(11L).when(tagDao).insert(any(Tag.class));
        Tag tag = tagList.get(1);
        Long tagId = tagService.insert(tag);
        verify(tagDao, times(1)).insert(Mockito.any(Tag.class));
        Assert.assertEquals(Long.valueOf(11), tagId);
    }

    @Test
    public void testDeleteTag() throws DaoException, ServiceException {
        tagService.delete(5L);
        verify(tagDao, times(1)).delete(Matchers.anyLong());
    }

    @Test
    public void findById() throws DaoException, ServiceException {

        Tag tag = new Tag("selebrity");
        doReturn(tag).when(tagDao).findById(anyLong());
        Tag actualTag = tagService.findById(1L);
        assertNotNull(actualTag);
        assertEquals(tag.getTagName(), actualTag.getTagName());

        verify(tagDao).findById(1L);
    }


//    @Test
//    public void  findByNewsId() throws DaoException, ServiceException {
//
//
//
//        doReturn(tagList).when(tagDao).findTagsByNewsId(anyLong());
//        List<Tag> actualTags= tagService.findTagsByNewsId(1L);
//        assertNotNull(actualTags);
//        assertEquals(5, actualTags.size());
//        verify(tagDao).findTagsByNewsId(1L);
//    }
}
