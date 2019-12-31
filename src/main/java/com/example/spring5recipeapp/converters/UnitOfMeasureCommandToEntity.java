package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.UnitOfMeasureCommand;
import com.example.spring5recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToEntity implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    // for this method Spring does not guarantee null safety
    // so we have to add @Synchronized annotation (this make the method synchronized and thread safe),
    // so we can run this in a multi-threaded environment
    @Synchronized
    @Nullable
    @Override
    // get one type and convert it to the other (in this case command object => entity)
    public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasureCommand) {
        if(unitOfMeasureCommand == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(unitOfMeasureCommand.getId());
        uom.setDescription(unitOfMeasureCommand.getDescription());
        return uom;
    }
}
