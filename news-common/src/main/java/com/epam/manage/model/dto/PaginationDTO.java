package com.epam.manage.model.dto;


import com.epam.manage.model.entity.News;


import java.util.List;

public class PaginationDTO {

    private List<News> newsList;
    private int totalNews;

    public PaginationDTO(List<News> newsList, int totalNews) {
        this.newsList = newsList;
        this.totalNews = totalNews;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public int getTotalNews() {
        return totalNews;
    }

    public void setTotalNews(int totalNews) {
        this.totalNews = totalNews;
    }
}
