package com.antisocial.controller;

import com.antisocial.service.CategoryService;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {

    /**
     * Category controller.
     * The only difference between this controller and main controller is
     * the scrollUrl model attribute.
     *
     * @author Ant Kaynak - Github/Exercon
     */

    private final CategoryService categoryService;
    private final UserService userService;


    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService){
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value="/category/{category}", method = RequestMethod.GET)
    public String articles(@PathVariable("category") String category, Model model, RedirectAttributes attr){
        if(!categoryService.isCategoryValid(category)){
            attr.addFlashAttribute("error","Any article with that category does not exist!");
            return "redirect:/oups";
        }
        model.addAttribute("userName", userService.getPrincipal());
        model.addAttribute("activeCategory", category);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("scrollUrl", "/get/category/"+category+"/");

        return "home";
    }

}
