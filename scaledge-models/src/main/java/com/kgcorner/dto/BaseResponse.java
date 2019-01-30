package com.kgcorner.dto;

public class BaseResponse {
    public enum RESPONSE_TYPE {
        ERROR,SUCCESS
    }

    private RESPONSE_TYPE type;
    private String message;

    public BaseResponse(RESPONSE_TYPE type, String message) {
        this.type = type;
        this.message = message;
    }

    public BaseResponse() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RESPONSE_TYPE getType() {
        return type;
    }

    public void setType(RESPONSE_TYPE type) {
        this.type = type;
    }
}
