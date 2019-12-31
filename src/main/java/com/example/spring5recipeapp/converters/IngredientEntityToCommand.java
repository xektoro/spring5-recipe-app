package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.IngredientCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientEntityToCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureEntityToCommand uomConverter;

    public IngredientEntityToCommand(UnitOfMeasureEntityToCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredientEntity) {
        if (ingredientEntity == null) {
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredientEntity.getId());
        if(ingredientEntity.getRecipe() != null) {
            ingredientCommand.setRecipeId(ingredientEntity.getRecipe().getId());
        }
        ingredientCommand.setAmount(ingredientEntity.getAmount());
        ingredientCommand.setDescription(ingredientEntity.getDescription());
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredientEntity.getUnitOfMeasure()));
        return ingredientCommand;
    }
}
