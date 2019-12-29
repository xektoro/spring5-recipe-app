package com.example.spring5recipeapp.controllers;

import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

// when we are testing controllers, we can do just unit tests (there in no need to bring the Spring Context, because
// it is making our test very slow)
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

    // here we will introduce a mock web server (or mock dispatcher servlet)
    @Test
    void testMockMvc() throws Exception {
        // if we specify MockMvcBuilders.webAppContextSetup() - we are going to bring Spring Context and
        // our tests will no longer be a unit tests.
        // but MockMvcBuilders.standaloneSetup() will keep our tests running nice and fast (not bringing Spring Context)
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
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