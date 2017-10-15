package com.antisocial.controller;

import com.antisocial.dto.UserSettingsDTO;
import com.antisocial.model.FileUpload;
import com.antisocial.model.User;
import com.antisocial.model.UserDetail;
import com.antisocial.service.ArticleService;
import com.antisocial.service.CategoryService;
import com.antisocial.service.CommentService;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * User Controller.
     * Handles user profile and settings page.
     * Uses initBinder to bind StringTrimmerEditor and trims strings.
     *
     *
     * @author Ant Kaynak - Github/Exercon
     */

    private final UserService userService;
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
    private final ArticleService articleService;
    private final CommentService commentService;


    @Autowired
    public UserController(UserService userService, CategoryService categoryService, PasswordEncoder passwordEncoder, ArticleService articleService, CommentService commentService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/{ssoId}", method = RequestMethod.GET)
    public String profile(@PathVariable("ssoId") String ssoId, Model model, RedirectAttributes attr){
        User user = userService.getUserBySsoId(ssoId, true);
        if(user == null){
            attr.addFlashAttribute("error","User does not exist");
            return "redirect:/error";
        }

        if(userService.isUserOnline(ssoId)){
            model.addAttribute("online", true);
        }else{
            model.addAttribute("online", false);
        }

        model.addAttribute("userName", userService.getPrincipal());
        model.addAttribute("profile", user);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("comments", commentService.getCommentsByUserSsoId(ssoId, 10));
        model.addAttribute("articles", articleService.getArticlesByUserID(user, 10));
        model.addAttribute("commentCount", commentService.getCommentCountBySsoId(ssoId));
        model.addAttribute("articleCount", articleService.getArticleCountByUserID(user));
        model.addAttribute("fileUpload", new FileUpload());
        if(ssoId.equals(userService.getPrincipal())){
            model.addAttribute("uploadOpt","available");
        }

        return "profile";
    }


    @RequestMapping(value = "/{ssoId}/settings", method = RequestMethod.GET)
    public String settings(@PathVariable("ssoId") String ssoId, Model model, RedirectAttributes attr){
        if(!ssoId.equals(userService.getPrincipal())){
            attr.addFlashAttribute("error","You are not allowed to visit this page.");
            return "redirect:/error";
        }
        User user = userService.getUserBySsoId(ssoId, false);
        if(user == null){
            attr.addFlashAttribute("error","User does not exist");
            return "redirect:/error";
        }
        if(!model.containsAttribute("userSettingsDTO")) {
            UserSettingsDTO userSettingsDTO = new UserSettingsDTO(user.getSsoId(), user.getEmail(), user.getName(), user.getUserDetail().getUserBio());
            model.addAttribute("userSettingsDTO", userSettingsDTO);
        }

        model.addAttribute("userName", userService.getPrincipal());
        model.addAttribute("categories", categoryService.getCategories());
        return "settings";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute UserSettingsDTO userSettingsDTO, BindingResult br, Model model, RedirectAttributes attr){
        if(br.hasErrors()){
            attr.addFlashAttribute("userSettingsDTO", userSettingsDTO);
            attr.addFlashAttribute("updateError", br.getAllErrors());
            return "redirect:/user/"+userSettingsDTO.getSsoId()+"/settings?updateError";
        }

        User user = userService.getUserBySsoId(userSettingsDTO.getSsoId(), true);
        if(user == null){
            attr.addFlashAttribute("error", "User does not exist.");
            return "redirect:/error";
        }
        if(!user.getSsoId().equals(userService.getPrincipal())){
            attr.addFlashAttribute("error", "You do not have the permission to access this page.");
            return "redirect:/error";
        }

        if(userService.isUserExistEmail(userSettingsDTO.getEmail()) && !user.getEmail().equals(userSettingsDTO.getEmail())){
            attr.addFlashAttribute("userSettingsDTO", userSettingsDTO);
            List<ObjectError> list = new ArrayList<ObjectError>();
            list.add(new ObjectError("userSettingsDTO","User with the given email already exists!"));
            attr.addFlashAttribute("updateError", list);
            return "redirect:/user/"+userSettingsDTO.getSsoId()+"/settings?updateError";
        }

        user.setEmail(userSettingsDTO.getEmail());
        /*if(!user.getPassword().equals(userDTO.getPassword())){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }*/
        /*if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }*/
        if (userSettingsDTO.getPassword() != null && userSettingsDTO.getPasswordChange() != null) {
            user.setPassword(passwordEncoder.encode(userSettingsDTO.getPassword()));
        }

        user.setName(userSettingsDTO.getName());
        user.getUserDetail().setUserBio(userSettingsDTO.getUserBio());

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Could not update user. Please contact our support team.");
            return "redirect:/error";
        }

        return "redirect:/user/"+userSettingsDTO.getSsoId()+"/settings?success";

    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
