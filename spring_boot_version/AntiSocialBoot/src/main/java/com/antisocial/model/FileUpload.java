package com.antisocial.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
 
    private MultipartFile file;

    private String deneme;
     
    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDeneme() {
        return deneme;
    }

    public void setDeneme(String deneme) {
        this.deneme = deneme;
    }
}