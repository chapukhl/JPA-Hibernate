package com.epam.manage.model.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Tag")
@NamedQueries({
        @NamedQuery(name = "Tag.getAll", query = "from Tag t ORDER BY  tagName"),
        @NamedQuery(name = "Tag.findTagsByNews", query = "from Tag t ORDER BY  tagName")
})
public class Tag {

    @Id
    @Column(name = "TAG_ID")
    @SequenceGenerator(name="TAG_SEQ", sequenceName="TAG_SEQ")
    @GeneratedValue(generator="TAG_SEQ")
    private Long tagId;

    @Basic
    @Column(name = "TAG_NAME")
    private String tagName;

    @ManyToMany(mappedBy="tags")
    List<News> newses;


    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public List<News> getNewses() {
        return newses;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (tagId != null ? !tagId.equals(tag.tagId) : tag.tagId != null) return false;
        if (tagName != null ? !tagName.equals(tag.tagName) : tag.tagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tagId != null ? tagId.hashCode() : 0;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}