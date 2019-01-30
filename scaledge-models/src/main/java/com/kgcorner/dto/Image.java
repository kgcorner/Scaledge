package com.kgcorner.dto;


import java.util.Date;
import java.util.List;


public class Image extends ImagePreview {

    private List<String> tags;
    private UserPreview uploader;
    private Date uploadDate;

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
}
