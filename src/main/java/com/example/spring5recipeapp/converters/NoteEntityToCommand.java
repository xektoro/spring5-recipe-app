package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.NoteCommand;
import com.example.spring5recipeapp.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteEntityToCommand implements Converter<Note, NoteCommand> {

    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(Note noteEntity) {
        if (noteEntity == null) {
            return null;
        }

        final NoteCommand noteCommand = new NoteCommand();
        noteCommand.setId(noteEntity.getId());
        noteCommand.setRecipeNote(noteEntity.getRecipeNote());
        return noteCommand;
    }
}
