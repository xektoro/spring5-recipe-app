package com.example.spring5recipeapp.bootstrap;

import com.example.spring5recipeapp.domain.*;
import com.example.spring5recipeapp.repositories.CategoryRepositoty;
import com.example.spring5recipeapp.repositories.RecipeRepository;
import com.example.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

// SlF4j is a logging facade. That is going to give us access to the default Spring Boot logger, which is the Logback
// this annotations injects a logger
@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepositoty categoryRepositoty;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepositoty categoryRepositoty, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepositoty = categoryRepositoty;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    // we direct the Spring Framework to create a transaction around this method.
    // So now everything is going to happen in the same transactional context an we won't see this
    // lazy initialization exception
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.save(getRecipe());
        log.debug("Loading Bootstrap Data ...");
    }

    //pretty ugly code
    private Recipe getRecipe() {
        Optional<UnitOfMeasure> eachUnitOfMeasureOptonal = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUnitOfMeasureOptonal.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUnitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUnitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUnitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUnitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupUnitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        // get Optionals
        UnitOfMeasure eachUOM = eachUnitOfMeasureOptonal.get();
        UnitOfMeasure tableSpoonUOM = tableSpoonUnitOfMeasureOptional.get();
        UnitOfMeasure teaSpoonUOM = teaSpoonUnitOfMeasureOptional.get();
        UnitOfMeasure dashUOM = dashUnitOfMeasureOptional.get();
        UnitOfMeasure cupUOM = cupUnitOfMeasureOptional.get();
        UnitOfMeasure pintUOM = pintUnitOfMeasureOptional.get();

        // get Categories
        Optional<Category> americanCategoryOptional = categoryRepositoty.findByDescription("American");

        if(!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepositoty.findByDescription("Mexican");

        if(!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = americanCategoryOptional.get();

        Recipe taco = new Recipe();
        taco.setDescription("Desc");
        taco.setCookTime(9);
        taco.setPrepTime(20);
        taco.setDifficulty(Difficulty.MEDIUM);
        taco.setDirections("1. adasd, 2. dasdas, 3. rtert");

        Note tacoNotes = new Note();
        // needed for bidirectioanl - should be one method call
        tacoNotes.setRecipeNote("balbalabla");

        // now this is not needed anymore, because we are doing that automatically in Recipe.setNotes(Note note
        // tacoNotes.setRecipe(taco);

        taco.setNote(tacoNotes);

        taco.getIngredients().add(new Ingredient("desc1", new BigDecimal(2), tableSpoonUOM, taco));
        taco.getIngredients().add(new Ingredient("desc2", new BigDecimal(10), teaSpoonUOM, taco));
        taco.getIngredients().add(new Ingredient("desc3", new BigDecimal(5), dashUOM, taco));

        return taco;
    }
}
