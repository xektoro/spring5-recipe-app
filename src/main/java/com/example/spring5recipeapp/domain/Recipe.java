package com.example.spring5recipeapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    // that means the underlying persistence framework will generate the ID value for us
    // it supports the automatic generation of a sequence
    // in the past that strategy did not work with Oracle database, now maybe it is working
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer serveTime;
    private String source;
    private String url;

    @Lob
    private String directions;

    // here we want the recipe to own this relationship
    // here in "mappedBy", we want to specify the property on the child class, which has the relations
    // so this defines a relationship from Recipe class and we are saying that this Recipe will get storedon a property on the child ("recipe")
    // mappedBy = "recipe" is the target property on the Ingredient class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    // this is going to be created as a binary large object field (BLOB) in the database
    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    // here for value, we have two EnumTypes - ordinary and string
    // ORDINAL is default and that is going to get, if we don't specify anything there
    // and it is going going to persist the enum values as Integers (1, 2 and 3)
    // STRING is going to get the String value (EASY, MEDIUM or HARD) and persist
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    // here in "joinColumns" and "inverseJoinColumns" we only specify the names of the columns in the db table
    // good practice is the both columns to be in singular
    // "joinColumns" will match column of this table and "inverseJoinColumns" will match column of the other table
    // so we want a table "recipe_category" and from this way of the relationship we are going to use recipe_id
    // and from the other category_id
    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    // these setter method is going to override the default one from Lombok!
    public void setNote(Note note) {
        this.note = note;
        // we do the two-way directional setting process relationship on one place - the second now is automatic
        note.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        // we do the two-way directional setting process relationship on one place - the second now is automatic
        ingredient.setRecipe(this);
        return this;
    }
}
