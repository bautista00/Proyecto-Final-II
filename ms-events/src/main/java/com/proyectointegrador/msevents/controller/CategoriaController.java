package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.Categoria;
import com.proyectointegrador.msevents.service.CategoriaService;
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
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/all")
    public List<Categoria> getAllCategorias() {
        List<Categoria> categorias = categoriaService.getAllCategorias();
        return categorias;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCategoriaById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (categoriaService.getCategoriaById(id).isEmpty()) {
            response = new ResponseEntity<>("No se encontro la categoria", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(categoriaService.getCategoriaById(id), HttpStatus.OK);
        }
        return response;
    }

}
