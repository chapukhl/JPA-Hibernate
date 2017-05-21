package com.epam.manage.model.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "News")
@SqlResultSetMappings( {@SqlResultSetMapping(name = "findFilteredNews", entities = {@EntityResult(entityClass = News.class)},
        columns={
                @ColumnResult(name="total_count")}),@SqlResultSetMapping(name = "getAllNews", entities = {@EntityResult(entityClass = News.class)})})
@NamedNativeQueries({
        @NamedNativeQuery(name = "News.findNextNews", query = "Select news_id_next FROM( SELECT " +
                "n.NEWS_ID," +
                " LAG(n.NEWS_ID) OVER (ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC) as NEWS_ID_NEXT " +
                " FROM news n LEFT JOIN (SELECT c.news_id, COUNT(c.news_id) " +
                " as comment_count FROM COMMENTS c GROUP BY c.news_id) c ON " +
                " n.NEWS_ID=c.news_id  ORDER BY NVL(c.comment_count,0) DESC,n.modification_date DESC) WHERE news_id=?"),
})
@NamedQueries({
        @NamedQuery(name = "News.getAll", query = "from News n"),


        @NamedQuery(name = "News.findTotalNewsNumber", query = "SELECT COUNT(*) as news_number FROM News"),

})
public class News {

    @Version
   // @Column(name = "VERSION")
    private int version;

    @Id
    @Column(name = "NEWS_ID")
    @SequenceGenerator(name="NEWS_SEQ", sequenceName="NEWS_SEQ")
    @GeneratedValue(generator="NEWS_SEQ")
    private Long  newsId;

    @Basic
    @Column(name = "SHORT_TEXT")
    private String shortText;

    @Basic
    @Column(name = "FULL_TEXT")
    private String fullText;


    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "MODIFICATION_DATE")
    private Date modificationDate;

    @OneToMany(mappedBy = "news",fetch=FetchType.EAGER)
    private Set<Comment> comments;




    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="NEWS_AUTHOR",
            joinColumns = @JoinColumn(name = "news_id",
                    referencedColumnName = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id",
                    referencedColumnName = "author_id")
    )
    private List<Author> authors;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="NEWS_TAG",
            joinColumns = @JoinColumn(name = "news_id",
                    referencedColumnName = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",
                    referencedColumnName = "tag_id")
    )
    Set<Tag> tags;

    public News() {

        this.authors=new LinkedList<>();
        this.tags=new HashSet<>();
        this.comments =new HashSet<>();
    }

    public News(Long newsId,String shortText, String fullText, String title, Date creationDate, Date modificationDate) {

        this.newsId=newsId;
        this.shortText = shortText;
        this.fullText = fullText;
        this.title = title;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.authors=new LinkedList<>();
        this.tags=new HashSet<>();
        this.comments =new HashSet<>();

    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> commentsList) {
        this.comments = commentsList;
    }


    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }


    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }


    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getModificationDate() {
        return modificationDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long [] findTagIds(){
        Long[] tagIds=new Long[tags.size()];
        int i=0;
        for(Tag tag:tags){
            tagIds[i]=tag.getTagId();
            i++;
        }
        return tagIds;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (creationDate != null ? !creationDate.equals(news.creationDate) : news.creationDate != null) return false;
        if (fullText != null ? !fullText.equals(news.fullText) : news.fullText != null) return false;
        if (modificationDate != null ? !modificationDate.equals(news.modificationDate) : news.modificationDate != null)
            return false;
        if (newsId != null ? !newsId.equals(news.newsId) : news.newsId != null) return false;
        if (shortText != null ? !shortText.equals(news.shortText) : news.shortText != null) return false;
        if (title != null ? !title.equals(news.title) : news.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = newsId != null ? newsId.hashCode() : 0;
        result = 31 * result + (shortText != null ? shortText.hashCode() : 0);
        result = 31 * result + (fullText != null ? fullText.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", commentsList=" + comments +
                ", authors=" + authors +
                ", tags=" + tags +
                '}';
    }



}
