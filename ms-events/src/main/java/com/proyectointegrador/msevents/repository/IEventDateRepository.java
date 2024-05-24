package com.proyectointegrador.msevents.repository;

import com.proyectointegrador.msevents.domain.EventDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventDateRepository extends JpaRepository<EventDate, Long> {
}
