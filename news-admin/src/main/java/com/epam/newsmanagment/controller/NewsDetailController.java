package com.epam.newsmanagment.controller;


import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Comment;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.ICommentService;
import com.epam.manage.service.IManageService;
import com.epam.manage.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
@RequestMapping("admin/news-detail")
public class NewsDetailController {

    @Autowired
    private IManageService newsManagementService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private INewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public String showNewsDetail(@RequestParam("id") Long newsId, Model model, HttpSession session) throws ServiceException {


        if (newsId != null) {

            News news = newsManagementService.findNewsById(newsId);
            model.addAttribute(news);
            model.addAttribute(new Comment());

            NewsFilter newsFilter = getNewsFilter(session);

            model.addAttribute("prevExist", newsService.findPreviousFilteredNewsId(newsFilter, newsId));
            model.addAttribute("nextExist", newsService.findNextFilteredNewsId(newsFilter, newsId));


        }


        return "news-detail";
    }

    private NewsFilter getNewsFilter(HttpSession session) {
        NewsFilter result = (NewsFilter) session.getAttribute("filterVO");
        if (result == null) {
            result = new NewsFilter(null, null);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add_comment")
    public String addComment(Comment comment,
                             Model model) throws ServiceException {
        if (comment.getCommentText() != null && !comment.getCommentText().isEmpty()) {
            comment.setCreationDate(new Date());
            News currentNews = newsService.findById(comment.getNews().getNewsId());
            comment.setNews(currentNews);
            commentService.insert(comment);
            model.addAttribute("id", comment.getNews().getNewsId());
        }

        return "redirect:/admin/news-detail";

    }

    @RequestMapping(value = "/delete_comment", method = RequestMethod.GET)
    public String deleteAuthor(@RequestParam("commentId") Long commentId, @RequestParam("newsId") Long newsId, Model model) throws ServiceException {

        if (commentId != null && newsId != null) {

            commentService.delete(commentId);
            model.addAttribute("id", newsId);
        }

        return "redirect:/admin/news-detail";
    }


    @RequestMapping(value = "/next_news", method = RequestMethod.GET)
    public String nextNews(@RequestParam("newsId") Long newsId, Model model, HttpSession session) throws ServiceException {

        StringBuilder view = new StringBuilder("redirect:/admin/news-detail");

        if (newsId != null) {

            Long nextId;
            NewsFilter newsFilter = getNewsFilter(session);

            nextId = newsService.findNextFilteredNewsId(newsFilter, newsId);
            if (nextId != null) {
                model.addAttribute("nextExist", newsService.findNextFilteredNewsId(newsFilter, nextId));
            }


            if (nextId != null && nextId != 0) {
                view.append("?id=").append(nextId);
            } else {
                view.append("?id=").append(newsId);

            }

        }


        return view.toString();
    }


    @RequestMapping(value = "/prev_news", method = RequestMethod.GET)
    public String previousNews(@RequestParam("newsId") Long newsId, Model model, HttpSession session) throws ServiceException {

        StringBuilder view = new StringBuilder("redirect:/admin/news-detail");

        if (newsId != null) {

            Long prevId;
            NewsFilter newsFilter = getNewsFilter(session);


            prevId = newsService.findPreviousFilteredNewsId(newsFilter, newsId);
            if (prevId != null) {
                model.addAttribute("prevExist", newsService.findPreviousFilteredNewsId(newsFilter, prevId));
            }

            if (prevId != null && prevId != 0) {

                view.append("?id=").append(prevId);


            } else {

                view.append("?id=").append(newsId);
            }
        }

        return view.toString();
    }


}