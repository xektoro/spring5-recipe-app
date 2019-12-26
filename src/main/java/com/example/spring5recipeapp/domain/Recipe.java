package com.example.spring5recipeapp.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServeTime() {
        return serveTime;
    }

    public void setServeTime(Integer serveTime) {
        this.serveTime = serveTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
        // we do the two-way directional setting process relationship on one place - the second now is automatic
        note.setRecipe(this);
    }

    //
    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        // we do the two-way directional setting process relationship on one place - the second now is automatic
        ingredient.setRecipe(this);
        return this;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
