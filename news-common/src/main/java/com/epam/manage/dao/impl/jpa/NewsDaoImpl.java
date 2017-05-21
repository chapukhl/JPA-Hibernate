package com.epam.manage.dao.impl.jpa;


import com.epam.manage.dao.INewsDao;
import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.DaoException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.News;
import com.epam.manage.util.QueryBuilder;
import org.hibernate.StaleObjectStateException;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Profile("JPA")
@Transactional(rollbackFor = {Exception.class})
public class NewsDaoImpl implements INewsDao {

    @PersistenceContext(unitName = "NewsManagement")
    private EntityManager entityManager;

    @Override
    public Long insert(News news) {

        entityManager.persist(news);

        return news.getNewsId();
    }

    @Override
    public void delete(Long[] ids) {

        String sqlQuery = QueryBuilder.buildParametersList("DELETE FROM news WHERE news_id in", ids, "");
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.executeUpdate();

    }

    @Override
    public News findById(Long id) {
        return entityManager.find(News.class, id);
    }

    @Override
    public void update(News news) throws DaoException {

        try {
            entityManager.merge(news);
        } catch (StaleObjectStateException e) {

            throw new DaoException(e);
        }


    }

    @Override
    public List<News> findAll(int startRecordNum, int endRecordNum) {

        Query query = entityManager.createNativeQuery("SELECT news_id,short_text,full_text,title,creation_date,modification_date,version FROM (SELECT " +
                " n.news_id,n.short_text,n.full_text,n.title,n.creation_date,n.modification_date, c.comment_count,version " +
                " FROM news n LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) " +
                " as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                " n.NEWS_ID=c.news_id  ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC)", "getAllNews");

        query.setFirstResult(startRecordNum - 1);

        query.setMaxResults(endRecordNum - startRecordNum + 1);

        return query.getResultList();

    }

    @Override
    public int findTotalNewsNumber() {

        return ((Number) entityManager.createNamedQuery("News.findTotalNewsNumber").getSingleResult()).intValue();

    }

    @Override
    public Long findNextNewsId(Long id) {

        Query query = entityManager.createNativeQuery("Select news_id_next FROM( SELECT " +
                "  n.NEWS_ID," +
                "   LAG(n.NEWS_ID) OVER (ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC) as NEWS_ID_NEXT " +
                "  FROM news n LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) " +
                "  as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                "  n.NEWS_ID=c.news_id  ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC) WHERE news_id=?").setParameter(1, id);

        Long resultId = checkResultId(query);

        return resultId;

    }


    @Override
    public Long findPreviousNewsId(Long id) {

        Query query = entityManager.createNativeQuery("Select news_id_prev FROM( SELECT " +
                " n.NEWS_ID, " +
                " LEAD(n.NEWS_ID) OVER (ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC) as NEWS_ID_PREV " +
                " FROM news n LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) " +
                " as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                " n.NEWS_ID=c.news_id  ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC) WHERE news_id=?").setParameter(1, id);


        return checkResultId(query);

    }

    @Override
    public PaginationDTO findFilteredNews(NewsFilter newsFilter, int startNewsNumber, int endNewsNumber) {

        String sqlQuery = QueryBuilder.newsFilterQuery(newsFilter, startNewsNumber, endNewsNumber);
        Query query = entityManager.createNativeQuery(sqlQuery, "findFilteredNews");
        List<Object[]> resultList = query.getResultList();
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

        Query query = entityManager.createNativeQuery(sqlQuery);

        return checkResultId(query);

    }

    @Override
    public Long findNextFilteredNewsId(NewsFilter newsFilter, Long id) {

        String sqlQuery = QueryBuilder.nextFilterNews(newsFilter, id);
        Query query = entityManager.createNativeQuery(sqlQuery);

        return checkResultId(query);

    }

    private Long checkResultId(Query query) {

        Object o = query.getSingleResult();

        return ((BigDecimal) o).longValue();
    }

}
