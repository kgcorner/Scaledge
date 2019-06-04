package com.kgcorner.scaledgecontent.services;

/*
Description : Content Service provides various content related functionalities
Author: kumar
Created on : 4/6/19
*/

import com.kgcorner.scaledgecontent.dao.model.Content;
import com.kgcorner.scaledgecontent.dao.repo.ContentDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    @Autowired
    private ContentDataRepo dataRepo;

    public Content createContent(Content content) {
        return dataRepo.create(content);
    }
}