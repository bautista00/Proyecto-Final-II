package com.proyectointegrador.msevents.service;

import com.proyectointegrador.msevents.domain.Evento;
import com.proyectointegrador.msevents.repository.IEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private IEventoRepository eventoRepository;

    public Optional<Evento> getEventoById(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento;
    }

    public List<Evento> getAllEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos;
    }

}
