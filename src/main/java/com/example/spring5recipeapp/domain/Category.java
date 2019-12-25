package com.example.spring5recipeapp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    // if we have not specified "mappedBy" JPA will create another table for our relationship
    // we add propery "mappedBy" to not secondary side of ManyToMany Relationship
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipies;

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

    public Set<Recipe> getRecipies() {
        return recipies;
    }

    public void setRecipies(Set<Recipe> recipies) {
        this.recipies = recipies;
    }
}
