package com.proyectointegrador.msestadio.controller;

import com.proyectointegrador.msestadio.domain.Asiento;
import com.proyectointegrador.msestadio.service.AsientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asiento")
public class AsientoController {

    @Autowired
    private AsientoService asientoService;

    @GetMapping("/all")
    public List<Asiento> getAllAsientos() {
        List<Asiento> asientos = asientoService.getAllAsientos();
        return asientos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAsientoById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (asientoService.getAsientoById(id).isEmpty()) {
            response = new ResponseEntity<>("Asiento no encontrado", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(asientoService.getAsientoById(id), HttpStatus.OK);
        }
        return response;
    }
}
