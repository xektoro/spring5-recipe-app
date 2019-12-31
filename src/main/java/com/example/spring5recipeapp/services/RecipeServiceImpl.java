package com.example.spring5recipeapp.services;

import com.example.spring5recipeapp.commands.RecipeCommand;
import com.example.spring5recipeapp.converters.RecipeCommandToEntity;
import com.example.spring5recipeapp.converters.RecipeEntityToCommand;
import com.example.spring5recipeapp.domain.Recipe;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j // it gives us a logger
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeEntityToCommand recipeEntityToCommand;
    private final RecipeCommandToEntity recipeCommandToEntity;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeEntityToCommand recipeEntityToCommand, RecipeCommandToEntity recipeCommandToEntity) {
        this.recipeRepository = recipeRepository;
        this.recipeEntityToCommand = recipeEntityToCommand;
        this.recipeCommandToEntity = recipeCommandToEntity;
    }

    @Override
    public Set<Recipe> getRecipes() {
        // this slf4j log is coming from Lombok
        log.debug("I'm in the service");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet :: add);

        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if(!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        // we convert it and still it is just a POJO, not a Hibernate object
        // It is still in detached from Hibernate context
        Recipe detachedRecipe = recipeCommandToEntity.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        // we convert it back
        return recipeEntityToCommand.convert(savedRecipe);
    }

    @Override
    // because we are doing conversion outside of the scope
    // so wif we hit any lazy loaded properties it will throw an exception
    // so we are extending the transactional scope to be that method
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeEntityToCommand.convert(findById(id));
    }
}
