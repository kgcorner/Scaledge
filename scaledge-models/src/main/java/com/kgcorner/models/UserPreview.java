package com.kgcorner.models;

public class UserPreview extends BaseModel {

    protected String name;

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


}
