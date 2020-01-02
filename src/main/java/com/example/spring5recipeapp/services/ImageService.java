package com.example.spring5recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(Long imageId, MultipartFile multipartFile);
}
