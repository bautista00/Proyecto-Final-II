package com.proyectointegrador.msevents.service;

import com.proyectointegrador.msevents.domain.Categoria;
import com.proyectointegrador.msevents.repository.ICategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;

    public Optional<Categoria> getCategoriaById(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria;
    }

    public List<Categoria> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias;
    }
}
