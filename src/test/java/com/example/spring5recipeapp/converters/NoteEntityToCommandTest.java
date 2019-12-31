package com.example.spring5recipeapp.converters;

import com.example.spring5recipeapp.commands.NoteCommand;
import com.example.spring5recipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteEntityToCommandTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";

    NoteEntityToCommand converter;


    @BeforeEach
    void setUp() {
        converter = new NoteEntityToCommand();
    }

    @Test
    public void testNull() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Note()));
    }

    @Test
    void convert() {
        //given
        Note notes = new Note();
        notes.setId(ID_VALUE);
        notes.setRecipeNote(RECIPE_NOTES);

        //when
        NoteCommand noteCommand = converter.convert(notes);

        //then
        assertEquals(ID_VALUE, noteCommand.getId());
        assertEquals(RECIPE_NOTES, noteCommand.getRecipeNotes());
    }
}