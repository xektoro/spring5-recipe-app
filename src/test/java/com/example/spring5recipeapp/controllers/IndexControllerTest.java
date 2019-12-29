package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IndexControllerTest {
    // here we need Mocks for our Service and Model data
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {

        // given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe rec = new Recipe();
        rec.setId(89L);
        recipes.add(rec);

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        String viewName = indexController.getIndexPage(model);

        // then
        // here we check that we are loading the right Thymeleaf template ("index")
        assertEquals("index", viewName);

        // verify that getRecipes() is called only once
        verify(recipeService, times(1)).getRecipes();
        // we are expecting to add attribute with the String "recipes" and with any set value
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        // we make sure, that we return a Set with 2 items
        assertEquals(2, setInController.size());
    }
}