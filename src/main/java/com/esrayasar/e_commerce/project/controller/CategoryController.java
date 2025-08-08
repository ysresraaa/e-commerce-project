package com.esrayasar.e_commerce.project.controller;


import com.esrayasar.e_commerce.project.dto.CategoryResponseDTO;
import com.esrayasar.e_commerce.project.dto.CategoryCreateDTO;
import com.esrayasar.e_commerce.project.entity.Category;
import com.esrayasar.e_commerce.project.service.ICategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;
    private final View error;
    private Object savedCategory;

    @Autowired
    public CategoryController(ICategoryService categoryService, ModelMapper modelMapper, View error) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.error = error;
    }
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        Category category = modelMapper.map(categoryCreateDTO, Category.class);
        Category savedCategory = categoryService.saveCategory(category);
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(savedCategory, CategoryResponseDTO.class);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>>getAllCategories(){
        List<Category>categories=categoryService.getAllCategories();
        List<CategoryResponseDTO>categoryResponseDTOs=categories.stream()
                .map(category -> modelMapper.map(category,CategoryResponseDTO.class))
                .collect(Collectors.toList());
        return  ResponseEntity.ok(categoryResponseDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> ResponseEntity.ok(modelMapper.map(category, CategoryResponseDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryCreateDTO categoryCreateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        Category category = modelMapper.map(categoryCreateDTO, Category.class);
        category.setId(id);
        Category updatedCategory = categoryService.updateCategory(id, category);
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(updatedCategory, CategoryResponseDTO.class);
        return ResponseEntity.ok(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}