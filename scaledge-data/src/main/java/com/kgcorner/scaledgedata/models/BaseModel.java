package com.kgcorner.scaledgedata.models;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseModel implements Serializable {
    @Id
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
