package com.kgcorner.scaledge.resourceutil;

/*
Description : makes the Resource exception aware
Author: kumar
Created on : 27/5/19
*/

import org.springframework.http.ResponseEntity;

public interface BaseExceptionAware<T> {
    T handleCustomException(IllegalArgumentException e);
}