package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.Evento;
import com.proyectointegrador.msevents.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/all")
    public List<Evento> getAllEventos() {
        return eventoService.getAllEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEventoById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (eventoService.getEventoById(id).isEmpty()) {
            response = new ResponseEntity<>("No se encontro el evento", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(eventoService.getEventoById(id), HttpStatus.OK);
        }
        return response;
    }
}
