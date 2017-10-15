package com.antisocial.controller;


import com.antisocial.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class MainController {

    /**
     * Main Controller.
     * Handles main page, about page, error and access denied page.
     *
     * @author Ant Kaynak - Github/Exercon
     */

    private final CategoryService categoryService;
    private final UserService userService;


    @Autowired
    public MainController(CategoryService categoryService, UserService userService){
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value= "/" , method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("userName", userService.getPrincipal());
        model.addAttribute("scrollUrl", "/get/home/");


        return "home";
    }

    @RequestMapping(value = "/error")
    public String error(Model model){
        if(!model.containsAttribute("error")){
            model.addAttribute("error", "You do not have the permission to access this page.");
        }
        if(userService.getPrincipal() != null){
            model.addAttribute("categories", categoryService.getCategories());
            model.addAttribute("userName", userService.getPrincipal());
        }
        return "error";
    }


    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String accessDeniedPage(RedirectAttributes attr) {
        attr.addFlashAttribute("error","You are not allowed to see this page!");
        return "redirect:/error";
    }



    @RequestMapping(value = "/about")
    public String about(Model model){
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("userName", userService.getPrincipal());
        return "about";
    }


}
