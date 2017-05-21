package com.epam.newsmanagment.controller;

import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.News;
import com.epam.manage.service.IManageService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/admin")
public class NewsController {

    @Autowired
    private IManageService manageService;

    @InitBinder
    public void initBinder(HttpServletRequest request,
                           ServletRequestDataBinder binder) {
        Locale locale = RequestContextUtils.getLocale(request);
        SimpleDateFormat dateFormat = null;
        if (locale.getLanguage().equals("ru")) {
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        }
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
                dateFormat, true));
    }

    @RequestMapping(value = "/save_news", method = RequestMethod.POST)
    public String saveNews(@RequestParam(value = "author", required = false) Long authorId, @RequestParam(value = "tag", required = false) Long[] tagIds, News news) throws ServiceException {
        if (news != null) {

            news.setModificationDate(news.getCreationDate());
            manageService.createNews(news, authorId, tagIds);

        }

        return "redirect:/admin/news-list";
    }

    @RequestMapping(value = "/update_news", method = RequestMethod.POST)
    public String updateNews(@RequestParam(value = "author", required = false) Long authorId, @RequestParam(value = "tag", required = false) Long[] tagIds, News news, Model model) {
        String view = "redirect:/admin/news-list";
        if (news != null) {
            news.setModificationDate(news.getCreationDate());
            try {
                manageService.updateNews(news, authorId, tagIds);
                model.addAttribute("unavailable", false);
            } catch (ServiceException e) {
                view = "redirect:/admin/update_news";
                model.addAttribute("unavailable", true);
            }
        }

        return view;
    }


}
