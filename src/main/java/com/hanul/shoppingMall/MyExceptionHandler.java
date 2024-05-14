/*
package com.hanul.shoppingMall;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MyExceptionHandler {

   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> urlError() {
        return ResponseEntity.status(400).body("URL 주소 에러");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> inputError(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "에러: " + ex.getMessage();
        return ResponseEntity.status(400).body(errorMessage);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> urlError() {
        return ResponseEntity.status(400).body("URL 주소 에러");
    }
}*/
