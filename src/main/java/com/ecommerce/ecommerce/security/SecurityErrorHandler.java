package com.ecommerce.ecommerce.security;

import com.ecommerce.ecommerce.DTO.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.nio.file.AccessDeniedException;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class SecurityErrorHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex){
        return new ResponseEntity<Object>(
                "You don't have permission to access this resource",
                new HttpHeaders(),
                FORBIDDEN
        );
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleBadCredentialsException(Exception ex){
        return  new ResponseEntity<Object>(
                new ErrorMessage(ex.getMessage(),new Date(System.currentTimeMillis())),
                new HttpHeaders(),
                BAD_REQUEST
        );
    }

}
