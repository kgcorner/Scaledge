package com.kgcorner.scaledgecontent.dao.model;


import com.kgcorner.scaledge.previewobjects.UserPreview;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document
public class Content implements Serializable  {

    private List<String> tags;
    private UserPreview uploader;
    private Date uploadDate;
    private String description;
    private String contentUrl;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public UserPreview getUploader() {
        return uploader;
    }

    public void setUploader(UserPreview uploader) {
        this.uploader = uploader;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }


    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
