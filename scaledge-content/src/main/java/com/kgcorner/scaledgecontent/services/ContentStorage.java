package com.kgcorner.scaledgecontent.services;

/*
Description : Defines contract for storage
Author: kumar
Created on : 4/6/19
*/

import org.springframework.web.multipart.MultipartFile;

public interface ContentStorage {
    String storeDocument(MultipartFile file, String name);
    String storeDocument(MultipartFile file);
}