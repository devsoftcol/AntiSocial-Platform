package com.antisocial.controller;

import com.antisocial.dto.CategoryDTO;
import com.antisocial.dto.UserDTO;
import com.antisocial.model.*;
import com.antisocial.service.CategoryService;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    /**
     * Controller for admin page.
     * Handles all of the admin tabs.
     * Some of the admin tabs are missing and are deprecated.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */

    private final CategoryService categoryService;
    private final UserService userService;


    @Autowired
    public AdminController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }



    @RequestMapping(value= {"","/"} , method = RequestMethod.GET)
    public String admin(Model model){
        model.addAttribute("userName", userService.getPrincipal());
        model.addAttribute("categories", categoryService.getCategories());
        if(!model.containsAttribute("userDTO")){
            model.addAttribute("userDTO", new UserDTO());
        }
        if(!model.containsAttribute("categoryDTO")){
            model.addAttribute("categoryDTO", new CategoryDTO());
        }
        model.addAttribute("fileUpload", new FileUpload());
        model.addAttribute("accountStates", getAccountStates());
        return "admin";
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute UserDTO userDTO, RedirectAttributes attr){
        User user = userService.getUserBySsoId(userDTO.getSsoId(), true);
        if( user == null){
            attr.addFlashAttribute("error", "User does not exist.");
            return "redirect:/error";
        }
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setState(userDTO.getState());
        user.getUserDetail().setType(userDTO.getType());

        try{
            userService.saveUser(user);
        }catch (Exception e){
            attr.addFlashAttribute("error", "An error occurred.");
            return "redirect:/error";
        }

        if(!userDTO.getState().equals("ACTIVE")){
            userService.kickUser(userDTO.getSsoId());
        }

        attr.addFlashAttribute("userDTO", userDTO);
        return "redirect:/admin";

    }

    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    public String updateCategory(@Valid @ModelAttribute CategoryDTO categoryDTO, BindingResult br, RedirectAttributes attr){
        if(br.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            for(ObjectError i : br.getAllErrors()){
                errorMsg.append(i.getDefaultMessage());
            }
            attr.addFlashAttribute("error", errorMsg.toString());
            return "redirect:/error";
        }
        Category category = categoryService.getCategoryByName(categoryDTO.getCategoryName());
        if(category == null){
            attr.addFlashAttribute("error", "Category not found with this name : "+ categoryDTO.getCategoryName());
            return "redirect:/error";
        }
        category.setCategoryName(categoryDTO.getCategoryNew());
        try{
            categoryService.saveCategory(category);
        }catch (Exception e){
            attr.addFlashAttribute("error", "Could not update category. Please contact our support team.");
            return "redirect:/error";
        }

        attr.addFlashAttribute("categoryDTO", categoryDTO);
        return "redirect:/admin";

    }

    @RequestMapping(value = "/category/create", method = RequestMethod.POST)
    public String createCategory(@Valid @ModelAttribute CategoryDTO categoryDTO, BindingResult br, RedirectAttributes attr){
        if(br.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            for(ObjectError i : br.getAllErrors()){
                errorMsg.append(i.getDefaultMessage());
            }
            attr.addFlashAttribute("error", errorMsg.toString());
            return "redirect:/error";
        }

        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryNew());
        try{
            categoryService.saveCategory(category);
            categoryService.uploadCategoryStarterFiles(categoryDTO.getCategoryNew());
        }catch (Exception e){
            attr.addFlashAttribute("error", "Could not create category. Please contact our support team.");
            return "redirect:/error";
        }

        attr.addFlashAttribute("categoryDTO", categoryDTO);
        return "redirect:/admin";

    }



    private List<String> getAccountStates(){
        List<String> list = new ArrayList<>();
        list.add(State.ACTIVE.getName());
        list.add(State.BANNED.getName());
        list.add(State.DELETED.getName());
        list.add(State.REGISTER.getName());
        return list;
    }


}
