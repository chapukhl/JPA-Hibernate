package com.epam.manage.model.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "AUTHOR")
@NamedQueries({
        @NamedQuery(name = "Author.getAll", query = "from Author a ORDER BY  name"),
        @NamedQuery(name = "Author.getAllActive", query = "from Author a WHERE a.expired is null ORDER BY  name"),
        @NamedQuery(name = "Author.expiredAuthor", query = "UPDATE Author  a SET a.expired=current_date WHERE a.authorId=:authorId")

})
public class Author implements Comparable {

    @Id
    @Column(name = "AUTHOR_ID")
    @SequenceGenerator(name="AUTHOR_SEQ", sequenceName="AUTHOR_SEQ")
    @GeneratedValue(generator="AUTHOR_SEQ")
    private Long authorId;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "EXPIRED")
    private Timestamp expired;

    @ManyToMany(mappedBy ="authors")
    List<News> newses;


    public Author() {
    }

    public Author(String name) {

        this.name = name;

    }



    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }


    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Timestamp getExpired() {
        return expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (authorId != null ? !authorId.equals(author.authorId) : author.authorId != null) return false;
        if (expired != null ? !expired.equals(author.expired) : author.expired != null) return false;
        if (name != null ? !name.equals(author.name) : author.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", expired=" + expired +
                '}';
    }


    @Override
    public int compareTo(Object o) {

        Author author=(Author)o;
        return this.getName().compareTo(author.getName());
    }
}
