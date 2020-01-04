package com.example.spring5recipeapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
// now this exception will be handled on global level (by all controllers in the application)
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(Exception exception) {

        log.error("Handling Number Format Exception");
        log.error(exception.getMessage());

        ModelAndView mav = new ModelAndView();
        // set the view name
        mav.setViewName("400error");
        mav.addObject("exception", exception);

        return mav;
    }
}
