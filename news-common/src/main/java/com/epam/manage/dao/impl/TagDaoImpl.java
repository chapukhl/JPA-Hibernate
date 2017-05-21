package com.epam.manage.dao.impl;


import com.epam.manage.dao.ITagDao;
import com.epam.manage.exception.DaoException;
import com.epam.manage.model.entity.Tag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.*;
import java.util.List;


@Service
@Transactional(rollbackFor = {Exception.class})
public class TagDaoImpl implements ITagDao {

    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    public Long insert(Tag tag) {


        Tag tagFromDB = entityManager.merge(tag);

        return tagFromDB.getTagId();
    }

    public void delete(Long id) {


        entityManager.remove(findById(id));

    }

    public Tag findById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    public void update(Tag tag) {

        entityManager.merge(tag);

    }

    public List<Tag> findAll() {
       Query namedQuery = entityManager.createNamedQuery("Tag.getAll");
        return namedQuery.getResultList();
    }

}
