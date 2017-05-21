package com.epam.manage.service.impl;

import com.epam.manage.dao.IAuthorDao;
import com.epam.manage.exception.DaoException;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Author;
import com.epam.manage.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements IAuthorService {

    @Autowired
    private IAuthorDao authorDao;

    @Override
    public List<Author> findAll() throws ServiceException {

            return authorDao.findAll();

    }

    public List<Author> findAllActive() throws ServiceException{

            return authorDao.findAllActive();

    }

    @Override
    public Author findById(Long id) throws ServiceException {

            return authorDao.findById(id);

    }

    @Override
    public void delete(Long id) throws ServiceException {


            authorDao.delete(id);


    }

    @Override
    public Long insert(Author author) throws ServiceException {

           return authorDao.insert(author);

    }

    @Override
    public void update(Author author) throws ServiceException {


            authorDao.update(author);


    }


}
