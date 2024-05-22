package com.proyectointegrador.msestadio.repository;

import com.proyectointegrador.msestadio.domain.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IZonaRepository extends JpaRepository<Zona, Long> {
}
