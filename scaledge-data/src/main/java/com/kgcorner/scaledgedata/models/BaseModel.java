package com.kgcorner.scaledgedata.models;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseModel implements Serializable {
    @Id
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks whether model is valid or not
     * @return
     */
    public boolean isValid() {
        return true;
    }
}
