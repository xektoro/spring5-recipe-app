package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.converters.RecipeCommandToEntity;
import com.example.spring5recipeapp.converters.RecipeEntityToCommand;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

// pure JUnit 5 test
// here we have the service layer and we inject in repositories and we want to test that business logic inside the service layer
// and we are injecting dependencies like the repository here and now we are using Mockito Mocks to test the business logic in the service layer
class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeEntityToCommand recipeEntityToCommand;

    @Mock
    RecipeCommandToEntity recipeCommandToEntity;

    @BeforeEach
    public void setUp() {
        // initialize out Mocks
        // So we are saying to Mockito - "give me a mock recipe repository"
        MockitoAnnotations.initMocks(this);

        // initialize out recipe service
        recipeService = new RecipeServiceImpl(recipeRepository, recipeEntityToCommand, recipeCommandToEntity);
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {
        Recipe recipe = new Recipe();
        HashSet recipesData = new HashSet();
        recipesData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        // When the recipe repository
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        // verify that method of recipeRepository (findALl()) was called only once, not twice or zero..
        // that is good way to check that interactions within the class are happening as expected
        verify(recipeRepository, times(1)).findAll();
    }
}