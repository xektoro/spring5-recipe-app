package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.IngredientCommand;
import com.example.spring5recipeapp.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToEntity implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToEntity uomConverter;

    public IngredientCommandToEntity(UnitOfMeasureCommandToEntity uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand command) {
        if (command == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(command.getId());
        ingredient.setAmount(command.getAmount());
        ingredient.setDescription(command.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(command.getUnitOfMeasure()));
        return ingredient;
    }
}
