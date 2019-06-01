package com.kgcorner.scaledge.resources.auth;

/*
Description : <Write class Description>
Author: kumar
Created on : 31/5/19
*/

import com.kgcorner.scaledge.resourceutil.BaseExceptionAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionAware implements BaseExceptionAware<ResponseEntity<String>> {
    @Override
    public ResponseEntity<String> handleCustomException(IllegalArgumentException e) {

        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}