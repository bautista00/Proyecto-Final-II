package com.proyectointegrador.msevents.service;

import com.proyectointegrador.msevents.domain.FechaEvento;
import com.proyectointegrador.msevents.repository.IFechaEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FechaEventoService {

    @Autowired
    private IFechaEventoRepository fechaEventoRepository;

    public Optional<FechaEvento> getFechaEventoById(Long id) {
        Optional<FechaEvento> fechaEvento = fechaEventoRepository.findById(id);
        return fechaEvento;
    }

    public List<FechaEvento> getAllFechaEventos() {
        List<FechaEvento> fechaEventos = fechaEventoRepository.findAll();
        return fechaEventos;
    }
}
