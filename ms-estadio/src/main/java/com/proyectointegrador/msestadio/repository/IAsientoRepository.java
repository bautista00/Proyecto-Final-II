package com.proyectointegrador.msestadio.repository;

import com.proyectointegrador.msestadio.domain.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAsientoRepository extends JpaRepository<Asiento, Long> {
}
