package com.proyectointegrador.msestadio.service;

import com.proyectointegrador.msestadio.domain.Asiento;
import com.proyectointegrador.msestadio.repository.IAsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AsientoService {

    @Autowired
    private IAsientoRepository asientoRepository;

    public Optional<Asiento> getAsientoById(Long id) {
        Optional<Asiento> asiento = asientoRepository.findById(id);
        return asiento;
    }

    public List<Asiento> getAllAsientos() {
        List<Asiento> asientos = asientoRepository.findAll();
        return asientos;
    }

}
