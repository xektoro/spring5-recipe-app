package com.example.spring5recipeapp.domain;

import javax.persistence.*;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // in this case we do not need to specify cascade, because the Recipe is the owning side
    // so in our case, we do not want on deletion of Note to delete also the associated Recipe
    @OneToOne
    private Recipe recipe;
    // this annotation is for large objects
    // because we want to put more than 255 characters (which is the default String in Hibernate and JPA)
    // so now the JPA is going to expect a value in CLOB (characters large object) field in the databbase

    @Lob
    private String recipeNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNote() {
        return recipeNote;
    }

    public void setRecipeNote(String recipeNote) {
        this.recipeNote = recipeNote;
    }
}
