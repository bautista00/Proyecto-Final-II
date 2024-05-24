package com.proyectointegrador.msevents.service.implement;

import com.proyectointegrador.msevents.domain.Category;
import com.proyectointegrador.msevents.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
