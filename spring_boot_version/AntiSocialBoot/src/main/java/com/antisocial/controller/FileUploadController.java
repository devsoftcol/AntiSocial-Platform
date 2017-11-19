package com.antisocial.controller;

import com.antisocial.model.FileUpload;
import com.antisocial.service.ImageService;
import com.antisocial.service.UserService;
import com.antisocial.validator.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class FileUploadController {

    /**
     * File Uploading Controller
     * Handles URL's corresponding to uploading data.
     * Uses initBinder to bind fileValidator and validate file data.
     * Uses ImageService to compress and save images.
     *
     * @author Ant Kaynak - Github/Exercon
     */

    private final FileValidator fileValidator;
    private final ServletContext servletContext;
    private final ImageService imageService;
    private final UserService userService;


    @Autowired
    public FileUploadController(FileValidator fileValidator, ServletContext servletContext, ImageService imageService, UserService userService) {
        this.fileValidator = fileValidator;
        this.servletContext = servletContext;
        this.imageService = imageService;
        this.userService = userService;
    }

    @RequestMapping(value="/user/{ssoId}/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String singleFileUpload(@Valid FileUpload fileUpload, BindingResult br, Model model, @PathVariable String ssoId, @RequestParam("source") String source, RedirectAttributes attr) throws IOException {
        if(!userService.getPrincipal().equalsIgnoreCase(ssoId)){
            return "redirect:/error";
        }
        if (br.hasErrors()) {
            attr.addFlashAttribute("uploadError", "Please upload a PNG or JPEG formatted file less than 1024 KB.");
            return "redirect:/user/"+ssoId+"?uploadError";
        }

        String SAVE_LOCATION;
        if(source.equalsIgnoreCase("bg")){
            SAVE_LOCATION = servletContext.getRealPath("/resources/") + "pic/bg/" + ssoId + ".jpg";
        }else if(source.equalsIgnoreCase("pp")){
            SAVE_LOCATION = servletContext.getRealPath("/resources/") + "pic/" + ssoId + ".jpg";
        }else {
            attr.addFlashAttribute("uploadError", "Unsupported URL. Please refresh the page and try again.");
            return "redirect:/user/"+ssoId+"?uploadError";
        }

        imageService.saveImage(fileUpload.getFile(),SAVE_LOCATION);

        return "redirect:/user/"+ssoId+"?successup";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/admin/category/update_img", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String singleFileUpload(@Valid FileUpload fileUpload, BindingResult br, @RequestParam("categoryName") String categoryName, RedirectAttributes attr) throws IOException {
        if (br.hasErrors()) {
            attr.addFlashAttribute("error", "Please upload a PNG or JPEG formatted file less than 1024 KB.");
            return "redirect:/error";
        }

        String SAVE_LOCATION = servletContext.getRealPath("/resources/") + "pic/cat/" + categoryName + ".jpg";


        imageService.saveImage(fileUpload.getFile(),SAVE_LOCATION);

        return "redirect:/admin";

    }


    @InitBinder("fileUpload")
    protected void initBinderFileBucket(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

}