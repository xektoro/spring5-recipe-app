package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.commands.RecipeCommand;
import com.example.spring5recipeapp.converters.RecipeCommandToEntity;
import com.example.spring5recipeapp.converters.RecipeEntityToCommand;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.exceptions.NotFoundException;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void getRecipes() {
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
        verify(recipeRepository, never()).findById(anyLong());
    }

    public void testDeleteById() {
        // given
        Long idToDelete = Long.valueOf(2L);

        // when
        recipeService.deleteById(idToDelete);

        // then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }


    @Test
    public void getRecipeByIdTestNotFound() throws Exception {

        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Exception exception = assertThrows(
                NotFoundException.class, () -> recipeService.findById(8L));

        //should go boom
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeEntityToCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }
}