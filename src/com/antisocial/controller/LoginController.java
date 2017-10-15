package com.antisocial.controller;

import com.antisocial.dto.UserDTO;
import com.antisocial.model.*;
import com.antisocial.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Controller
public class LoginController {

    /**
     * User Login Controller.
     * Handles login page actions and URL endpoints.
     * Uses initBinder to bind StringTrimmerEditor and trims strings.
     * Handles user logins, registers and activation tokens.
     * Uses BCryptPasswordEncoder to encode passwords.
     *
     * @author Ant Kaynak - Github/Exercon
     */


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;


    @Autowired
    public LoginController(UserService userService , PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model, HttpServletRequest request){
        if(userService.getPrincipal() != null){ return "redirect:/"; }
        if(!model.containsAttribute("userDTO")){
            model.addAttribute("userDTO", new UserDTO());
        }

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult br, Model model, RedirectAttributes attr, HttpServletRequest request){
        if(br.hasErrors()){
            attr.addFlashAttribute("userDTO", userDTO);
            attr.addFlashAttribute("errorBr", br.getAllErrors());
            return "redirect:login?registerError";
        }

        User user = new User(userDTO.getSsoId(),userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getName(), State.REGISTER.getName());
        UserDetail userDetail = new UserDetail("USER", userDTO.getUserBio());
        user.setUserDetail(userDetail);
        try {
            userService.saveUser(user);
            userService.uploadStarterFiles(userDTO.getSsoId());
            String appUrl = request.getRequestURL().toString();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
        }catch(Exception ex){
            ex.printStackTrace();
            attr.addFlashAttribute("userDTO", userDTO);
            attr.addFlashAttribute("errorSv", "An error occurred. Please try again.");
            return "redirect:login?registerError";
        }

        return "redirect:login?success";
    }

    @RequestMapping(value = "/register/registrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token, Model model, RedirectAttributes attr) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            attr.addFlashAttribute("error", "Invalid token.");
            return "redirect:/error";
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            attr.addFlashAttribute("error", "Token is expired.");
            return "redirect:/error";
        }
        user.setState(State.ACTIVE.getName());
        userService.deleteVerificationToken(token);
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login?success";
    }



    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
