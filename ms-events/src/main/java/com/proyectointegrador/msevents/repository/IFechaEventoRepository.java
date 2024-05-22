package com.proyectointegrador.msevents.repository;

import com.proyectointegrador.msevents.domain.FechaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFechaEventoRepository extends JpaRepository<FechaEvento, Long> {
}
