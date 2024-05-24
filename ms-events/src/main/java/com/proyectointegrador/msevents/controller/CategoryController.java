package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.Category;
import com.proyectointegrador.msevents.service.implement.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCategoryById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (categoryService.getCategoryById(id).isEmpty()) {
            response = new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
        }
        return response;
    }

}
