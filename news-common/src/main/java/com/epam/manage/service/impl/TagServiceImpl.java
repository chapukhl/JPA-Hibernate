package com.epam.manage.service.impl;

import com.epam.manage.dao.ITagDao;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Tag;
import com.epam.manage.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    private ITagDao tagDao;

    @Override
    public List<Tag> findAll() throws ServiceException {

            return tagDao.findAll();

    }

    @Override
    public Tag findById(Long id) throws ServiceException {


            return tagDao.findById(id);

    }

    @Override
    public void delete(Long id) throws ServiceException {


            tagDao.delete(id);

    }

    @Override
    public Long insert(Tag tag) throws ServiceException {


           return tagDao.insert(tag);

    }

    @Override
    public void update(Tag tag) throws ServiceException {

            tagDao.update(tag);

    }

//    @Override
//    public List<Tag> findTagsByNewsId(Long newsId) throws ServiceException {
//        try {
//            return tagDao.findTagsByNewsId(newsId);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//    }
}
