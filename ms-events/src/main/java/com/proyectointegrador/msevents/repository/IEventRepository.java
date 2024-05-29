package com.proyectointegrador.msevents.repository;

import com.proyectointegrador.msevents.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.id = ?1 ORDER BY e.name")
    Optional<Event> findEventById(Long id);

    @Query("SELECT e FROM Event e WHERE e.name = ?1 ORDER BY e.name")
    Optional<Event> findEventByName(String name);

    List<Event> findByPlaceId(Long id);
}
