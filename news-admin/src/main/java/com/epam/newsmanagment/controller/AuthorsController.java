package com.epam.newsmanagment.controller;

import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Author;
import com.epam.manage.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AuthorsController {

    @Autowired
    private IAuthorService authorService;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public String showAuthors(Model model) throws ServiceException {


        List<Author> authors = authorService.findAllActive();

        model.addAttribute("authors", authors);
        model.addAttribute(new Author());
        model.addAttribute("newAuthor", new Author());

        return "authors-edit";
    }

    @RequestMapping(value = "/delete_author", method = RequestMethod.GET)
    public String deleteAuthor(@RequestParam("id") Long authorId) throws ServiceException {

        if (authorId != null) {

            authorService.delete(authorId);
        }

        return "redirect:/admin/authors";
    }


    @RequestMapping(value = "/update_author", method = RequestMethod.POST)
    public String updateTag(Author author) throws ServiceException {
        if (author != null) {

            authorService.update(author);
        }

        return "redirect:/admin/authors";
    }


    @RequestMapping(value = "/save_author", method = RequestMethod.POST)
    public String saveTag(Author author) throws ServiceException {
        if (author != null && !author.getName().isEmpty()) {
            authorService.insert(author);
        }

        return "redirect:/admin/authors";
    }
}
