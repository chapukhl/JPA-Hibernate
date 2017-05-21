package com.epam.manage.dbhelper;

import java.util.Arrays;


public  class NewsFilter {

    private  Long authorId;

    private  Long[] tagIds;


    public NewsFilter(Long authorId, Long[] tagIds) {
        this.authorId = authorId;
        this.tagIds = tagIds;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long[] getTagIds() {


        return tagIds;
    }

    @Override
    public String toString() {
        return "FilterVO{" +
                "authorId=" + authorId +
                ", tagIds=" + Arrays.toString(tagIds) +
                '}';
    }
}
