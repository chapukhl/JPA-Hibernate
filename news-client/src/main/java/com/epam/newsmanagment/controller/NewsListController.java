package com.epam.newsmanagment.controller;


import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.Author;
import com.epam.manage.model.entity.Comment;
import com.epam.manage.model.entity.News;
import com.epam.manage.model.entity.Tag;
import com.epam.manage.service.IAuthorService;
import com.epam.manage.service.IManageService;
import com.epam.manage.service.INewsService;
import com.epam.manage.service.ITagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;


@Controller
@RequestMapping("/")
public class NewsListController {

    private static final Logger logger = LoggerFactory.getLogger(NewsListController.class);

    public static final int NEWS_ON_PAGE = 5;

    @Autowired
    private IManageService newsManagementService;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private ITagService tagService;

    @Autowired
    private INewsService newsService;


    @RequestMapping(value = {"/", "/news-list"}, method = RequestMethod.GET)
    public String showNewsList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model, HttpSession session) throws ServiceException {

        PaginationDTO paginationDTO = newsManagementService.findAllNewsOnPage(pageNumber, NEWS_ON_PAGE);
        List<News> newsVOList = paginationDTO.getNewsList();
        List<Author> authorsList = authorService.findAll();
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("newsList", newsVOList);
        model.addAttribute("authorList", authorsList);
        model.addAttribute("tagList", tagList);
        setFilterItems(null, null, session);
        int totalPagesNumber = (int) Math.ceil((double) paginationDTO.getTotalNews() / NEWS_ON_PAGE);
        model.addAttribute("pagesNumber", totalPagesNumber);

        session.setAttribute("filter", false);


        return "news-list";
    }


    @RequestMapping(value = {"/", "/filter"}, method = {RequestMethod.POST})
    public String filterNewsList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestParam(value = "author", required = false) Long authorId, @RequestParam(value = "tag", required = false) Long[] tagIds,
                                 Model model, HttpSession session) throws ServiceException {


        NewsFilter newsFilter = new NewsFilter(authorId, tagIds);
        PaginationDTO paginationDTO = newsManagementService.findByAuthorAndTags(newsFilter, pageNumber, NEWS_ON_PAGE);
        List<Author> authorsList = authorService.findAll();
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("newsList", paginationDTO.getNewsList());
        model.addAttribute("authorList", authorsList);
        model.addAttribute("tagList", tagList);

        setFilterItems(authorId, tagIds, session);


        int totalPagesNumber = (int) Math.ceil((double) paginationDTO.getTotalNews() / NEWS_ON_PAGE);
        model.addAttribute("pagesNumber", totalPagesNumber);
        session.setAttribute("filterVO", newsFilter);
        session.setAttribute("filter", true);


        return "news-list";
    }

    private void setFilterItems(Long authorId, Long[] tagIds, HttpSession session) {
        session.setAttribute("selectedAuthor", authorId);
        session.setAttribute("selectedTags", tagIds);
    }

    @RequestMapping(value = {"/", "/filter"}, method = {RequestMethod.GET})
    public String filterNewsListPage(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     Model model, HttpSession session) throws ServiceException {


        NewsFilter newsFilter = (NewsFilter) session.getAttribute("filterVO");

        PaginationDTO paginationDTO = newsManagementService.findByAuthorAndTags(newsFilter, pageNumber, NEWS_ON_PAGE);
        List<Author> authorsList = authorService.findAll();
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("newsList", paginationDTO.getNewsList());
        model.addAttribute("authorList", authorsList);
        model.addAttribute("tagList", tagList);


        int totalPagesNumber = (int) Math.ceil((double) paginationDTO.getTotalNews() / NEWS_ON_PAGE);
        model.addAttribute("pagesNumber", totalPagesNumber);
        session.setAttribute("filter", true);


        return "news-list";
    }


    @RequestMapping(value = "/news-detail", method = RequestMethod.GET)
    public String showNewsDetail(@RequestParam("id") Long newsId, Model model) throws ServiceException {

        if (newsId != null) {

            News news = newsManagementService.findNewsById(newsId);
            model.addAttribute(news);

            model.addAttribute(new Comment());

        }


        return "news-detail";
    }

}