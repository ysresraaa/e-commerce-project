package com.esrayasar.e_commerce.project.service;

import com.esrayasar.e_commerce.project.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    Category saveCategory(Category category);

    List<Category> getAllCategories();

    Optional<Category>getCategoryById(Long id);

    Category updateCategory(Long id,Category categoryDetails);

    void deleteCategory(Long id);



}
