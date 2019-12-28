package com.example.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
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
}
