package com.example.spring5recipeapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    // @Before annotation is renamed to @BeforeEach
    // this method will run before each test method execution in that class
    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        // Autoboxing
        Long idValue = 4L;
        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipies() {
    }
}