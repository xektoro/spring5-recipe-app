package com.example.spring5recipeapp.repositories;

import com.example.spring5recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepositoty extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
