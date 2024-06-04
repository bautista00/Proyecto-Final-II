package com.proyectointegrador.msevents.service.interfaces;

import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.dto.EventGetDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEventService {

    Optional<EventGetDTO> getEventById(Long id);
    Optional<EventGetDTO> getEventByName(String name);

    Set<EventGetDTO> getAllEvents();

    EventDTO addEvent(EventDTO eventDTO);
    EventDTO updateEvent(EventDTO eventDTO);

    void deleteEventById(Long id);
    void deleteEventByName(String name);

    List<Event> findByPlaceId(Long id);
}
