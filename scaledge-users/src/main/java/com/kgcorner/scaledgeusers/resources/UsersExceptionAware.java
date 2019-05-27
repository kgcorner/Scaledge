package com.kgcorner.scaledgeusers.resources;

/*
Description : <Write is class Description>
Author: kumar
Created on : 27/5/19
*/

import com.kgcorner.scaledge.resourceutil.BaseExceptionAware;
import com.kgcorner.scaledgedata.models.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class UsersExceptionAware implements BaseExceptionAware <ResponseEntity<String>> {
    private static final Logger LOGGER  = Logger.getLogger(UsersExceptionAware.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleCustomException(IllegalArgumentException e) {
        LOGGER.error(e.getLocalizedMessage());
        BaseResponse errorResponse = new BaseResponse( BaseResponse.RESPONSE_TYPE.ERROR, e.getLocalizedMessage());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}