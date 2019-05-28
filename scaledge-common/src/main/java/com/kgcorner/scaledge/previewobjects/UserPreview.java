package com.kgcorner.scaledge.previewobjects;

public class UserPreview {

    private String name;
    private String id;

    public UserPreview(){}
    public UserPreview(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
