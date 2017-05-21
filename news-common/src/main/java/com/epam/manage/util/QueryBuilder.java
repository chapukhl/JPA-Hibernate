package com.epam.manage.util;


import com.epam.manage.dbhelper.NewsFilter;

public class QueryBuilder {


    public static String buildParametersList(String firstPart, Long[] rowIds, String secondPart) {


        StringBuilder query = new StringBuilder(firstPart + "(");
        for (int i = 0; i < rowIds.length; i++) {
            if (i != rowIds.length - 1) {
                query.append(rowIds[i] + ",");
            } else {
                query.append(rowIds[i]);
            }
        }
        query.append(")");
        query.append(secondPart);

        return query.toString();
    }

    public static String newsFilterQuery(NewsFilter newsFilter, int startNewsNumber, int endNewsNumber) {

        Long authorId = newsFilter.getAuthorId();
        Long[] tagIds = newsFilter.getTagIds();

        StringBuilder query = new StringBuilder("SELECT news_id,short_text,full_text,title,creation_date,modification_date, comment_count,rownum news_number,total_count" +
                " FROM (SELECT news_id,short_text,full_text,title,creation_date,modification_date,comment_count,rownum news_number,COUNT(*) OVER () total_count FROM (SELECT DISTINCT  " +
                "n.news_id,n.short_text,n.full_text,n.title,n.creation_date,n.modification_date, c.comment_count " +
                "FROM news n ");
        if (authorId != null) {
            query.append("INNER JOIN news_author " +
                    "ON n.news_id=news_author.news_id INNER JOIN author  ON " +
                    "news_author.author_id=author.author_id AND author.author_id=").append(authorId);
        }

        if (tagIds != null) {
            query.append(buildParametersList(" JOIN news_tag nt ON n.NEWS_ID=nt.NEWS_ID AND nt.TAG_ID IN", tagIds, ""));
        }
        query.append(" LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) " +
                "as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                "n.NEWS_ID=c.news_id   ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC)) WHERE news_number>=")
                .append(startNewsNumber)
                .append(" AND news_number<=").append(endNewsNumber);

        return query.toString();
    }


    public static String prevFilterNews(NewsFilter newsFilter, Long id) {

        Long authorId = newsFilter.getAuthorId();
        Long[] tagIds = newsFilter.getTagIds();

        StringBuilder query = new StringBuilder("SELECT NEWS_ID_PREV FROM( SELECT LEAD(news_id) OVER " +
                " (ORDER BY NVL(comment_count,0) DESC,modification_date DESC) as NEWS_ID_PREV,NEWS_ID FROM(SELECT DISTINCT n.NEWS_ID,n.MODIFICATION_DATE,comment_count " +
                "  FROM news n ");

        if (authorId != null) {
            query.append("INNER JOIN news_author ON n.news_id=news_author.news_id " +
                    " INNER JOIN author  ON  news_author.author_id=author.author_id AND  author.author_id=").append(authorId);
        }

        if (tagIds != null) {
            query.append(buildParametersList(" JOIN news_tag nt ON n.NEWS_ID=nt.NEWS_ID AND nt.TAG_ID IN", tagIds, ""));
        }
        query.append("LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                " n.NEWS_ID=c.news_id)) WHERE NEWS_ID=").append(id);


        return query.toString();


    }

    public static String nextFilterNews(NewsFilter newsFilter, Long id) {

        Long authorId = newsFilter.getAuthorId();
        Long[] tagIds = newsFilter.getTagIds();


        StringBuilder query = new StringBuilder("SELECT NEWS_ID_NEXT FROM(SELECT LAG(news_id) OVER " +
                " (ORDER BY NVL(comment_count,0) DESC,modification_date DESC) as NEWS_ID_NEXT,NEWS_ID FROM(SELECT DISTINCT n.NEWS_ID,n.MODIFICATION_DATE,comment_count " +
                "  FROM news n ");

        if (authorId != null) {
            query.append("INNER JOIN news_author ON n.news_id=news_author.news_id " +
                    " INNER JOIN author  ON  news_author.author_id=author.author_id AND  author.author_id=").append(authorId);
        }

        if (tagIds != null) {
            query.append(buildParametersList(" JOIN news_tag nt ON n.NEWS_ID=nt.NEWS_ID AND nt.TAG_ID IN", tagIds, ""));
        }
        query.append("LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                " n.NEWS_ID=c.news_id)) WHERE NEWS_ID=").append(id);


        return query.toString();

    }


}
