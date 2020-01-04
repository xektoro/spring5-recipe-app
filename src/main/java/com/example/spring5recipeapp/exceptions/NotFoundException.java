package com.example.spring5recipeapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// if we comment the annotation we will and try to access an not existing recipe
// (for example http://localhost:8080/recipe/999/show), this will trigger RecipeServiceImpl.findById(Long id)
// we will have the default Response Status 500, but this way we can override the response status
// and the server will show us now 404, instead of 500
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
