package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.Category;
import com.proyectointegrador.msevents.dto.CategoryDTO;
import com.proyectointegrador.msevents.service.implement.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/public/getById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        ResponseEntity response = null;
        Optional<CategoryDTO> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            response = new ResponseEntity<>(category, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Category with id of: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/public/getByName/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        ResponseEntity response = null;
        Optional<CategoryDTO> category = categoryService.getCategoryByName(name);
        if (category.isPresent()) {
            response = new ResponseEntity<>(category, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Category with name: " + name + " not found.", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/public/get/all")
    public ResponseEntity<?> getAllCategories() {
        ResponseEntity response = null;
        Set<CategoryDTO> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            response = new ResponseEntity("No categories.", HttpStatus.NO_CONTENT);
        }
        else {
            response = new ResponseEntity<>(categories, HttpStatus.OK);
        }
        return response;
    }

    @PostMapping("/private/add")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO newCategoryDTO = categoryService.addCategory(categoryDTO);
            return new ResponseEntity<>("Category created successfully - " + newCategoryDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating a category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/private/update")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO newCategoryDTO = categoryService.updateCategory(categoryDTO);
            return new ResponseEntity<>("Category updated successfully - " + newCategoryDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating the category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/private/deleteById/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return new ResponseEntity<>("Category with the id of: " + id + " successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting category with id of: " + id + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/private/deleteByName/{name}")
    public ResponseEntity<?> deleteCategoryByName(@PathVariable String name) {
        try {
            categoryService.deleteCategoryByName(name);
            return new ResponseEntity<>("Category with the name: " + name + " successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting category with the name: " + name + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
