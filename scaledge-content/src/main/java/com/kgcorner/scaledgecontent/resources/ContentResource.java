package com.kgcorner.scaledgecontent.resources;

/*
Description : Represents ContentResource's endpoint
Author: kumar
Created on : 4/6/19
*/

import com.kgcorner.scaledge.previewobjects.UserPreview;
import com.kgcorner.scaledge.util.Constants;
import com.kgcorner.scaledge.util.Strings;
import com.kgcorner.scaledgecontent.dao.model.Content;
import com.kgcorner.scaledgecontent.services.ContentService;
import com.kgcorner.scaledgecontent.services.ContentStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@RestController
public class ContentResource {



    @Autowired
    private ContentStorage storage;

    @Autowired
    private ContentService service;

    @Value("${aws.s3.url}")
    private String s3Url;

    @PostMapping(value = "/contents",  consumes = Constants.PRODUCES_MULTIPART_FORM_DATA)
    public Content createContent(
        @RequestParam("tags") String tags,
        @RequestParam("description") String description,
        @RequestParam("userId") String userId,
        @RequestParam("name") String name,
        @RequestParam("image") MultipartFile image
        ) throws IOException {
        if(image.isEmpty())
            throw new IllegalArgumentException("Image can't be empty");
        String url = storage.storeDocument(image);
        if(url == null)
            throw new IllegalArgumentException("Can't upload image");
        url = s3Url + url;
        Content content = new Content();
        if(!Strings.isNullOrEmpty(tags))
            content.setTags(Arrays.asList(tags.split(",")));
        content.setDescription(description);
        content.setContentUrl(url);
        content.setUploadDate(new Date());
        if(Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("uploader's name can't be empty");
        if(Strings.isNullOrEmpty(userId))
            throw new IllegalArgumentException("uploader's id can't be empty");
        UserPreview preview = new UserPreview(userId, name);
        content.setUploader(preview);
        content = service.createContent(content);
        return content;
    }
}