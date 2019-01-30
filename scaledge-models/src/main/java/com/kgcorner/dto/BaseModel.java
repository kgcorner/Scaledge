package com.kgcorner.dto;

import java.io.Serializable;

public class BaseModel implements Serializable {

    protected String id;

    public String getId() {
        if(id.startsWith("ObjectId(")) {
            return id.replace("ObjectId(\"","").replace("\")","");
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
