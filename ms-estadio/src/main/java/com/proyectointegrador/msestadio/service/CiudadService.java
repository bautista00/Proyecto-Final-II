package com.proyectointegrador.msestadio.service;

import com.proyectointegrador.msestadio.domain.Ciudad;
import com.proyectointegrador.msestadio.repository.ICiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CiudadService {

    @Autowired
    private ICiudadRepository ciudadRepository;

    public Optional<Ciudad> getCiudadById(Long id) {
        Optional<Ciudad> ciudad = ciudadRepository.findById(id);
        return ciudad;
    }

    public List<Ciudad> getAllCiudades() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        return ciudades;
    }
}
