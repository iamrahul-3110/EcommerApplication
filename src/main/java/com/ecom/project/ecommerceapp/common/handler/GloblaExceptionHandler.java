package com.ecom.project.ecommerceapp.common.handler;

import com.ecom.project.ecommerceapp.common.exception.ApiException;
import com.ecom.project.ecommerceapp.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

// now onwards we can implement global exception handling here using @ControllerAdvice and @ExceptionHandler annotations and this is handle all exceptions across the application
@RestControllerAdvice // this will intercept exceptions from all controllers
//@ExceptionHandler // specify the exception types to handle here or can be specified on methods
public class GloblaExceptionHandler { // centralized exception handling

    @ExceptionHandler(MethodArgumentNotValidException.class) // handle validation exceptions globally
    public ResponseEntity<Map<String, String>> myMethodArgsNotValidHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Map<String,String >>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> myResourceNotFoundHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
     public ResponseEntity<String> myApiExceptionHandler(ApiException ex) {
         String message = ex.getMessage();
         return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
