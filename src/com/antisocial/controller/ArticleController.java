package com.antisocial.controller;

import com.antisocial.dto.ArticleDTO;
import com.antisocial.dto.CommentDTO;
import com.antisocial.model.Article;
import com.antisocial.model.Category;
import com.antisocial.model.Comment;
import com.antisocial.model.User;
import com.antisocial.service.ArticleService;
import com.antisocial.service.CategoryService;
import com.antisocial.service.CommentService;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController {

    /**
     * Main Article Controller.
     * Handles creating article and displaying single article page.
     * Main page and category pages are handled by another controller.
     *
     * @author Ant Kaynak - Github/Exercon
     */


    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, CategoryService categoryService, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @RequestMapping(value="/{articleID}", method = RequestMethod.GET)
    public String articles(@PathVariable("articleID") Long articleID, Model model, RedirectAttributes attr){
        Article article = articleService.getArticleById(articleID);
        if(article == null) {
            attr.addFlashAttribute("error","An article with that title does not exist!");
            return "redirect:/error";
        }
        model.addAttribute("userName", userService.getPrincipal());
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("commentDTO", new CommentDTO());
        model.addAttribute("scrollUrl", "/get/article/"+articleID);
        return "single_article";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createArticleGET(Model model){
        if(!model.containsAttribute("articleDTO")){
            model.addAttribute("articleDTO", new ArticleDTO());
        }
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("userName", userService.getPrincipal());
        return "article_add";
    }

    @RequestMapping(value= "/add", method = RequestMethod.POST)
    public String createArticle(@Valid @ModelAttribute ArticleDTO articleDTO, BindingResult br, RedirectAttributes attr){
        if(br.hasErrors()){
            attr.addFlashAttribute("articleDTO", articleDTO);
            attr.addFlashAttribute("errorBr", br.getAllErrors());
            return "redirect:/article/add";
        }
        User user = userService.getUserBySsoId(userService.getPrincipal() , true);
        Category category = categoryService.getCategoryByName(articleDTO.getCategory());
        if(category == null){
            attr.addFlashAttribute("error","An error occurred while selecting article category. Please contact our support team.");
            return "redirect:/error";
        }
        Article article = new Article(
                articleDTO.getArticleHeader(),
                articleDTO.getArticleBody()
        );
        userService.addArticle(article, user.getID());
        categoryService.addArticle(article, category.getID());
        articleService.saveArticle(article);
        return "redirect:/category/"+ articleDTO.getCategory();
    }

}
