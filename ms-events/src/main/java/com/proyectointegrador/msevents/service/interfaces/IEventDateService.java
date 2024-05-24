package com.proyectointegrador.msevents.service.interfaces;

import com.proyectointegrador.msevents.dto.EventDateDTO;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface IEventDateService {

    Optional<EventDateDTO> getEventDateById(Long id);
    Optional<EventDateDTO> getEventDateByDate(Date date);

    Set<EventDateDTO> getAllEventDates();

    EventDateDTO addEventDate(EventDateDTO eventDateDTO);
    EventDateDTO updateEventDate(EventDateDTO eventDateDTO);

    void deleteDateEventById(Long id);
    void deleteDateEventByDate(Date date);

}
