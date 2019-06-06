package com.kgcorner.scaledgecontent.services;

/*
Description : Defines contract for storage
Author: kumar
Created on : 4/6/19
*/

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ContentStorage {
    String storeDocument(MultipartFile file, String name) throws IOException;
    String storeDocument(MultipartFile file) throws IOException;
}