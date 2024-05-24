package com.proyectointegrador.msevents.repository;

import com.proyectointegrador.msevents.domain.DateEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IDateEventRepository extends JpaRepository<DateEvent, Long> {

    @Query("SELECT ed FROM EventDate ev WHERE ev.id = ?1 ORDER BY ev.date")
    Optional<DateEvent> findDateEventById(Long id);

    @Query("SELECT ed FROM EventDate ev WHERE ev.date = ?1")
    Optional<DateEvent> findDateEventByDate(Date date);

}
