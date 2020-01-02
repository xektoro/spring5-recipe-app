package com.example.spring5recipeapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
// this is going to exclude from the generation of "equals" and "hashCode" this variables
// because we have here bidirectional relationship and we get circular dependecy, so wi need need to exclude these
// properties from "equals" and "hashCode" methods
@EqualsAndHashCode(exclude = {"recipies"})
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

}
