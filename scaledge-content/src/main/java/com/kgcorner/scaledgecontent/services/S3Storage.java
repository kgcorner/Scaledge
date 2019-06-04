package com.kgcorner.scaledgecontent.services;

/*
Description : <Write class Description>
Author: kumar
Created on : 4/6/19
*/

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Storage implements ContentStorage{
    @Override
    public String storeDocument(MultipartFile file, String name) {
        return null;
    }

    @Override
    public String storeDocument(MultipartFile file) {
        return null;
    }
}