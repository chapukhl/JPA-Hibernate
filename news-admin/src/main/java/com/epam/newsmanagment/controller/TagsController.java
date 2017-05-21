package com.epam.newsmanagment.controller;

import com.epam.manage.exception.ServiceException;
import com.epam.manage.model.entity.Tag;
import com.epam.manage.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagsController {


    @Autowired
    private ITagService tagService;

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public String showTags(Model model) throws ServiceException {


        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        model.addAttribute(new Tag());
        model.addAttribute("newTag", new Tag());


        return "tags-edit";
    }

    @RequestMapping(value = "/delete_tag", method = RequestMethod.GET)
    public String deleteTag(@RequestParam("id") String tagId) throws ServiceException {

        if (tagId != null && !tagId.isEmpty()) {
            Long id = Long.parseLong(tagId);

            tagService.delete(id);
        }

        return "redirect:/admin/tags";
    }


    @RequestMapping(value = "/update_tag", method = RequestMethod.POST)
    public String updateTag(Tag tag) throws ServiceException {
        if (tag != null) {

            tagService.update(tag);

        }

        return "redirect:/admin/tags";
    }


    @RequestMapping(value = "/save_tag", method = RequestMethod.POST)
    public String saveTag(Tag tag) throws ServiceException {
        if (tag.getTagName() != null && !tag.getTagName().isEmpty()) {

            tagService.insert(tag);

        }

        return "redirect:/admin/tags";
    }
}