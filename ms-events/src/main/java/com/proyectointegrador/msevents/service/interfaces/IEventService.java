package com.proyectointegrador.msevents.service.interfaces;

import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEventService {

    Optional<EventDTO> getEventById(Long id);
    Optional<EventDTO> getEventByName(String name);

    Set<EventDTO> getAllEvents();

    EventDTO addEvent(EventDTO eventDTO);
    EventDTO updateEvent(EventDTO eventDTO);

    void deleteEventById(Long id);
    void deleteEventByName(String name);

    List<Event> findByPlaceId(Long id);
}
