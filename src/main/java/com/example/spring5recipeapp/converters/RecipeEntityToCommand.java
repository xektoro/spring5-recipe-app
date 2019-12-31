package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.RecipeCommand;
import com.example.spring5recipeapp.domain.Category;
import com.example.spring5recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeEntityToCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryEntityToCommand categoryConverter;
    private final IngredientEntityToCommand ingredientConverter;
    private final NoteEntityToCommand notesConverter;

    public RecipeEntityToCommand(CategoryEntityToCommand categoryConverter, IngredientEntityToCommand ingredientConverter,
                                 NoteEntityToCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe entity) {
        if (entity == null) {
            return null;
        }

        final RecipeCommand command = new RecipeCommand();
        command.setId(entity.getId());
        command.setCookTime(entity.getCookTime());
        command.setPrepTime(entity.getPrepTime());
        command.setDescription(entity.getDescription());
        command.setDifficulty(entity.getDifficulty());
        command.setDirections(entity.getDirections());
        command.setServeTime(entity.getServeTime());
        command.setSource(entity.getSource());
        command.setUrl(entity.getUrl());
        command.setNote(notesConverter.convert(entity.getNote()));

        if (entity.getCategories() != null && entity.getCategories().size() > 0){
            entity.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryConverter.convert(category)));
        }

        if (entity.getIngredients() != null && entity.getIngredients().size() > 0){
            entity.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
