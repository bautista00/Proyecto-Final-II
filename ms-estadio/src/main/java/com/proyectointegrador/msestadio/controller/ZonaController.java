package com.proyectointegrador.msestadio.controller;

import com.proyectointegrador.msestadio.domain.Zona;
import com.proyectointegrador.msestadio.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/zona")
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @GetMapping("/all")
    public List<Zona> getAllZonas() {
        List<Zona> zonas = zonaService.getAllZonas();
        return zonas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getZonaById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (zonaService.getZonaById(id).isEmpty()) {
            response = new ResponseEntity<>("No se encontro la zona", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(zonaService.getZonaById(id), HttpStatus.OK);
        }
        return response;
    }
}
