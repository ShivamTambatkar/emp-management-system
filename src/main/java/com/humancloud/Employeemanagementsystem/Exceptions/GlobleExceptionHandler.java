package com.humancloud.Employeemanagementsystem.Exceptions;

import com.humancloud.Employeemanagementsystem.Helper.ApiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message=ex.getMessage();
        ApiResponce apiResponce= new ApiResponce(message,false);
        return new ResponseEntity<ApiResponce>(apiResponce, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponce> unauthorizedExceptionHandler(UnauthorizedException ex){
        String message=ex.getMessage();
        ApiResponce apiResponce= new ApiResponce(message,false);
        return new ResponseEntity<ApiResponce>(apiResponce, HttpStatus.UNAUTHORIZED);
    }

}
