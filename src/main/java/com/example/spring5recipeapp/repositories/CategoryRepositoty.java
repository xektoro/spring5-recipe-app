package com.example.spring5recipeapp.repositories;

import com.example.spring5recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepositoty extends CrudRepository<Category, Long> {
}
