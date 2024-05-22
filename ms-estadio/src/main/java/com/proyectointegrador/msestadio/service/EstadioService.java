package com.proyectointegrador.msestadio.service;

import com.proyectointegrador.msestadio.domain.Estadio;
import com.proyectointegrador.msestadio.repository.IEstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadioService {

    @Autowired
    private IEstadioRepository estadioRepository;

    public Optional<Estadio> getEstadioById(Long id) {
        Optional<Estadio> estadio = estadioRepository.findById(id);
        return estadio;
    }

    public List<Estadio> getAllEstadios() {
        List<Estadio> estadios = estadioRepository.findAll();
        return estadios;
    }
}
