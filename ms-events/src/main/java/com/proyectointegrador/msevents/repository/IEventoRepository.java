package com.proyectointegrador.msevents.repository;

import com.proyectointegrador.msevents.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Long> {

}
