package com.proyectointegrador.msestadio.repository;

import com.proyectointegrador.msestadio.domain.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstadioRepository extends JpaRepository<Estadio, Long> {
}
