package com.proyectointegrador.msestadio.controller;

import com.proyectointegrador.msestadio.domain.Estadio;
import com.proyectointegrador.msestadio.service.EstadioService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estadio")
public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    @GetMapping("/all")
    public List<Estadio> getAllEstadios() {
        List<Estadio> estadios = estadioService.getAllEstadios();
        return estadios;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEstadioById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (estadioService.getEstadioById(id).isEmpty()) {
            response = new ResponseEntity<>("No se encontro el estadio", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(estadioService.getEstadioById(id), HttpStatus.OK);
        }
        return response;
    }
}
