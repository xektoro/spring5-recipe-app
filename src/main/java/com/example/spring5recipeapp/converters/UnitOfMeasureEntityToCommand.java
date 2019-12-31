package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureEntityToCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure uomEntity) {

        if (uomEntity != null) {
            final UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
            uomCommand.setId(uomEntity.getId());
            uomCommand.setDescription(uomEntity.getDescription());
            return uomCommand;
        }
        return null;
    }
}
