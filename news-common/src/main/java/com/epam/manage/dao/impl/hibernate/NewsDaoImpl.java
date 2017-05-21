package com.epam.manage.dao.impl.hibernate;


import com.epam.manage.dao.INewsDao;
import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.DaoException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.News;
import com.epam.manage.util.QueryBuilder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@Profile("Hibernate")
@Transactional(rollbackFor = {Exception.class})
public class NewsDaoImpl implements INewsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long insert(News news) {

        sessionFactory.getCurrentSession().save(news);

        return news.getNewsId();
    }

    @Override
    public void delete(Long[] ids) {

        Query query = sessionFactory.getCurrentSession().createQuery("DELETE FROM News n where n.newsId in :newsIds").setParameterList("newsIds",ids);
        query.executeUpdate();

    }

    @Override
    public News findById(Long id) {

        return (News)sessionFactory.getCurrentSession().get(News.class, id);
    }

    @Override
    public void update(News news) throws DaoException {


        sessionFactory.getCurrentSession().update(news);

    }

    @Override
    public List<News> findAll(int startRecordNum, int endRecordNum) {



        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT news_id,short_text,full_text,title,creation_date,modification_date FROM NEWS_VW").addEntity(News.class);


                query.setFirstResult(startRecordNum);

        query.setMaxResults(endRecordNum - startRecordNum + 1);


        return query.list();

    }

    @Override
    public int findTotalNewsNumber() {


        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(News.class);
        criteria.setProjection(Projections.rowCount());

        return  ((Number)criteria.uniqueResult()).intValue();

    }

    @Override
    public Long findNextNewsId(Long id) {

        Query query =  sessionFactory.getCurrentSession().createSQLQuery("Select news_id_next FROM NEXT_NEWS WHERE news_id=?").setParameter(0, id);

        return checkResultId(query);

    }


    @Override
    public Long findPreviousNewsId(Long id) {

        Query query = sessionFactory.getCurrentSession().createSQLQuery("Select news_id_prev FROM PREV_NEWS WHERE news_id=?").setParameter(0, id);


        return checkResultId(query);

    }

    @Override
    public PaginationDTO findFilteredNews(NewsFilter newsFilter, int startNewsNumber, int endNewsNumber) {

        String sqlQuery = QueryBuilder.newsFilterQuery(newsFilter, startNewsNumber, endNewsNumber);
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .addEntity(News.class).addScalar("total_count");
        List<Object[]> resultList = query.list();
        int totalNewsCount = 0;

        List<News> newsList = new ArrayList<>();

        for (Object[] record : resultList) {

            News news = (News) record[0];
            newsList.add(news);
            totalNewsCount = ((BigDecimal) record[1]).intValue();
        }

        return new PaginationDTO(newsList, totalNewsCount);
    }


    @Override
    public Long findPreviousFilteredNewsId(NewsFilter newsFilter, Long id) {

        String sqlQuery = QueryBuilder.prevFilterNews(newsFilter, id);
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);

        return checkResultId(query);

    }

    @Override
    public Long findNextFilteredNewsId(NewsFilter newsFilter, Long id) {

        String sqlQuery = QueryBuilder.nextFilterNews(newsFilter, id);
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);

        return checkResultId(query);

    }

    private Long checkResultId(Query query) {
        Object o=query.uniqueResult();

        return ((BigDecimal)o).longValue();
    }

}
