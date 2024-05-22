package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.FechaEvento;
import com.proyectointegrador.msevents.service.FechaEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fechaEvento")
public class FechaEventoController {

    @Autowired
    private FechaEventoService fechaEventoService;

    @GetMapping("/all")
    public List<FechaEvento> getAllFechaEventos() {
        List<FechaEvento> fechaEventos = fechaEventoService.getAllFechaEventos();
        return fechaEventos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getFechaEventoById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (fechaEventoService.getFechaEventoById(id).isEmpty()) {
            response = new ResponseEntity<>("No se encontro la fecha del evento", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(fechaEventoService.getFechaEventoById(id), HttpStatus.OK);
        }

        return response;
    }
}
