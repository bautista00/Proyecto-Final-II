package com.proyectointegrador.msestadio.service;

import com.proyectointegrador.msestadio.domain.Zona;
import com.proyectointegrador.msestadio.repository.IZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ZonaService {

    @Autowired
    private IZonaRepository zonaRepository;

    public Optional<Zona> getZonaById(Long id) {
        Optional<Zona> zona = zonaRepository.findById(id);
        return zona;
    }

    public List<Zona> getAllZonas() {
        List<Zona> zonas = zonaRepository.findAll();
        return zonas;
    }

}
