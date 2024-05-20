package com.hanul.shoppingMall.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMemberNotFoundException(MemberNotFoundException ex) {
        return ex.getMessage();
    }

/*
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
*/


}
