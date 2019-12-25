package com.example.spring5recipeapp.domain;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
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
    private String directions;

    // here we want the recipe to own this relationship
    // here in "mappedBy", we want to specify the property on the child class, which has the relations
    // so this defines a relationship from Recipe class and we are saying that this Recipe will get storedon a property on the child ("recipe")
    // mappedBy = "recipe" is the target property on the Ingredient class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    // this is going to be created as a binary large object field (BLOB) in the database
    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

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

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
