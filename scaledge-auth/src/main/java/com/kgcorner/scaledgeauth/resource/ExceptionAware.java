package com.kgcorner.scaledgeauth.resource;


import com.kgcorner.scaledgeauth.exception.AuthenticationFailedException;
import com.kgcorner.scaledgeauth.exception.InvalidRefreshTokenException;
import com.kgcorner.scaledgeauth.exception.TokenVerificationFailedException;
import com.kgcorner.scaledgedata.models.BaseResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionAware {

    private static final Logger LOGGER = Logger.getLogger(ExceptionAware.class);

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<String> handleCustomException(AuthenticationFailedException e) {
        LOGGER.error(e.getLocalizedMessage());
        BaseResponse errorResponse = new BaseResponse( BaseResponse.RESPONSE_TYPE.ERROR, e.getLocalizedMessage());
        return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenVerificationFailedException.class)
    public ResponseEntity<String> handleCustomException(TokenVerificationFailedException e) {
        LOGGER.error(e.getLocalizedMessage());
        BaseResponse errorResponse = new BaseResponse( BaseResponse.RESPONSE_TYPE.ERROR, e.getLocalizedMessage());
        return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<String> handleCustomException(InvalidRefreshTokenException e) {
        LOGGER.error(e.getLocalizedMessage());
        BaseResponse errorResponse = new BaseResponse( BaseResponse.RESPONSE_TYPE.ERROR, e.getLocalizedMessage());
        return new ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleCustomException(IllegalArgumentException e) {
        LOGGER.error(e.getLocalizedMessage());
        BaseResponse errorResponse = new BaseResponse( BaseResponse.RESPONSE_TYPE.ERROR, e.getLocalizedMessage());
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
