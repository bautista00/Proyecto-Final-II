package com.proyectointegrador.msestadio.repository;

import com.proyectointegrador.msestadio.domain.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICiudadRepository extends JpaRepository<Ciudad, Long> {
}
