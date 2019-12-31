package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.CategoryCommand;
import com.example.spring5recipeapp.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category categoryEntity) {
        if (categoryEntity == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(categoryEntity.getId());
        categoryCommand.setDescription(categoryEntity.getDescription());

        return categoryCommand;
    }

}
