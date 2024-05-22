package com.proyectointegrador.msestadio.controller;

import com.proyectointegrador.msestadio.domain.Ciudad;
import com.proyectointegrador.msestadio.service.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/all")
    public List<Ciudad> getAllCiudades() {
        List<Ciudad> ciudades = ciudadService.getAllCiudades();
        return ciudades;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCiudadById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (ciudadService.getCiudadById(id).isEmpty()) {
            response = new ResponseEntity<>("No se encontro la ciudad", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(ciudadService.getCiudadById(id), HttpStatus.OK);
        }
        return response;
    }
}
