package com.epam.newsmanagment.controller;

import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.dto.PaginationDTO;
import com.epam.manage.model.entity.Author;
import com.epam.manage.model.entity.News;
import com.epam.manage.model.entity.Tag;
import com.epam.manage.service.IAuthorService;
import com.epam.manage.service.IManageService;
import com.epam.manage.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class NewsListController {

    public static final int NEWS_ON_PAGE = 4;

    @Autowired
    private IManageService newsManagementService;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private ITagService tagService;


    @InitBinder
    public void initBinder(HttpServletRequest request,
                           ServletRequestDataBinder binder) {
        Locale locale = RequestContextUtils.getLocale(request);
        SimpleDateFormat dateFormat;
        if (locale.getLanguage().equals("ru")) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        }
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
                dateFormat, true));
    }

    @RequestMapping(value = {"/", "/news-list"}, method = RequestMethod.GET)
    public String showNewsList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, Model model, HttpSession session) throws ServiceException {

        PaginationDTO paginationDTO = newsManagementService.findAllNewsOnPage(pageNumber, NEWS_ON_PAGE);
        List<Author> authorsList = authorService.findAll();
        List<Tag> tagList = tagService.findAll();
        addModelParameters(model, paginationDTO, authorsList, tagList);
        int totalPagesNumber = (int) Math.ceil((double) paginationDTO.getTotalNews() / NEWS_ON_PAGE);
        model.addAttribute("pagesNumber", totalPagesNumber);
        session.setAttribute("filterVO", null);
        setFilterItems(null, null, session);

        return "news-list";
    }

    @RequestMapping(value = {"/", "/filter"}, method = {RequestMethod.POST})
    public String filterNewsList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber, @RequestParam(value = "author", required = false) Long authorId, @RequestParam(value = "tag", required = false) Long[] tagIds,
                                 Model model, HttpSession session) throws ServiceException {

        NewsFilter newsFilter = new NewsFilter(authorId, tagIds);
        PaginationDTO paginationDTO = newsManagementService.findByAuthorAndTags(newsFilter, pageNumber, NEWS_ON_PAGE);
        List<Author> authorsList = authorService.findAll();
        List<Tag> tagList = tagService.findAll();
        addModelParameters(model, paginationDTO, authorsList, tagList);
        int totalPagesNumber = (int) Math.ceil((double) paginationDTO.getTotalNews() / NEWS_ON_PAGE);
        model.addAttribute("pagesNumber", totalPagesNumber);
        setFilterItems(authorId, tagIds, session);
        session.setAttribute("filterVO", newsFilter);


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

        PaginationDTO paginationDTO = prepareAndSetModelAttributes(pageNumber, model, newsFilter);


        int totalPagesNumber = (int) Math.ceil((double) paginationDTO.getTotalNews() / NEWS_ON_PAGE);
        model.addAttribute("pagesNumber", totalPagesNumber);


        return "news-list";
    }

    private PaginationDTO prepareAndSetModelAttributes(Integer pageNumber, Model model, NewsFilter newsFilter) throws ServiceException {

        PaginationDTO paginationDTO = newsManagementService.findByAuthorAndTags(newsFilter, pageNumber, NEWS_ON_PAGE);
        List<Author> authorsList = authorService.findAll();
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("newsList", paginationDTO.getNewsList());
        model.addAttribute("authorList", authorsList);
        model.addAttribute("tagList", tagList);

        return paginationDTO;
    }

    @RequestMapping(value = {"/", "/delete_news"}, method = RequestMethod.POST)
    public String filterNewsList(@RequestParam(value = "deleteItem", required = false) Long[] newsIds, HttpSession session) throws ServiceException {

        String view = "redirect:/admin/news-list";

        newsManagementService.deleteNews(newsIds);
        NewsFilter newsFilter = (NewsFilter) session.getAttribute("filterVO");

        if (newsFilter != null) {
            view = "redirect:/admin/filter";
        }
        return view;
    }


    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String addNews(Model model) throws ServiceException {

        List<Author> authorsList = null;
        List<Tag> tagList = null;

        authorsList = authorService.findAllActive();
        tagList = tagService.findAll();


        model.addAttribute("News", new News());
        model.addAttribute("authorList", authorsList);
        model.addAttribute("tagList", tagList);

        return "news-add";
    }

    @RequestMapping(value = "/news-edit", method = RequestMethod.GET)
    public String editNews(@RequestParam("id") Long newsId, Model model) throws ServiceException {


        if (newsId != null) {

            News news = newsManagementService.findNewsById(newsId);
            List<Author> authorsList = authorService.findAllActive();
            List<Tag> tagList = tagService.findAll();

            if (!authorsList.contains(news.getAuthors().get(0))) {
                authorsList.add(news.getAuthors().get(0));
                Collections.sort(authorsList);
            }
            model.addAttribute("News", news);
            model.addAttribute("authorList", authorsList);
            model.addAttribute("tagList", tagList);

            if (news.getAuthors().get(0) != null) {

                model.addAttribute("selectedAuthor", news.getAuthors().get(0).getAuthorId());
            }

            model.addAttribute("selectedTags", news.findTagIds());


        }


        return "news-edit";
    }


    private void addModelParameters(Model model, PaginationDTO paginationDTO, List<Author> authorsList, List<Tag> tagList) {
        model.addAttribute("newsList", paginationDTO.getNewsList());
        model.addAttribute("authorList", authorsList);
        model.addAttribute("tagList", tagList);
    }
}