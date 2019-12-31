package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.NoteCommand;
import com.example.spring5recipeapp.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToEntity implements Converter<NoteCommand, Note> {

    @Synchronized
    @Nullable
    @Override
    public Note convert(NoteCommand noteCommand) {
        if(noteCommand == null) {
            return null;
        }

        final Note noteEntity = new Note();
        noteEntity.setId(noteCommand.getId());
        noteEntity.setRecipeNote(noteCommand.getRecipeNote());
        return noteEntity;
    }
}
