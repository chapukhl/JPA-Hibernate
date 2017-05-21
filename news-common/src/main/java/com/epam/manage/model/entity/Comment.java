package com.epam.manage.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "Comments")
@NamedQueries({
        @NamedQuery(name = "Comment.getAll", query = "SELECT c from Comment c"),

})
public class Comment {


    @Id
    @Column(name = "COMMENT_ID")
    @SequenceGenerator(name="COMMENTS_SEQ", sequenceName="COMMENTS_SEQ")
    @GeneratedValue(generator="COMMENTS_SEQ")
    private Long  commentId;

    @Basic
    @Column(name = "COMMENT_TEXT")
    private String commentText;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="NEWS_ID")
    private News news;


    public Comment() {
    }

    public Comment(String commentText, Date creationDate) {
        this.commentText = commentText;
        this.creationDate = creationDate;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }


    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (commentId != null ? !commentId.equals(comment.commentId) : comment.commentId != null) return false;
        if (commentText != null ? !commentText.equals(comment.commentText) : comment.commentText != null)
            return false;
        if (creationDate != null ? !creationDate.equals(comment.creationDate) : comment.creationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", commentText='" + commentText + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
