package com.epam.newsmanagment.controller;


import com.epam.manage.dbhelper.NewsFilter;
import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Comment;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.ICommentService;
import com.epam.manage.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Locale;


@Controller
@RequestMapping("/news-detail")
public class ViewNewsController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private INewsService newsService;

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

        return "redirect:/news-detail";

    }

    @RequestMapping(value = "/next_news", method = RequestMethod.GET)
    public String nextNews(@RequestParam("newsId") Long newsId, Model model, HttpSession session) throws ServiceException {

        StringBuilder view = new StringBuilder("redirect:/news-detail");

        if (newsId != null) {

            Long nextId = null;
            if ((Boolean) session.getAttribute("filter")) {
                NewsFilter newsFilter = (NewsFilter) session.getAttribute("filterVO");
                nextId = newsService.findNextFilteredNewsId(newsFilter, newsId);
            } else {
                nextId = newsService.findNextNewsId(newsId);
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

        StringBuilder view = new StringBuilder("redirect:/news-detail");

        if (newsId != null) {

            Long prevId;
            if ((Boolean) session.getAttribute("filter")) {
                NewsFilter newsFilter = (NewsFilter) session.getAttribute("filterVO");
                prevId = newsService.findPreviousFilteredNewsId(newsFilter, newsId);
            } else {
                prevId = newsService.findPreviousNewsId(newsId);
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