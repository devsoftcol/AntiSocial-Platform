package com.antisocial.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {

    public void compressSave(MultipartFile file, String uploadLocation) throws IOException;

    public void saveImage(MultipartFile file, String uploadLocation) throws IOException;


}
