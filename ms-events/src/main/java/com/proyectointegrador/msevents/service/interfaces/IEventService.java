package com.proyectointegrador.msevents.service.interfaces;

import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEventService {

    Optional<EventDTO> getEventById(Long id);
    Optional<EventDTO> getEventByName(String name);

    Set<EventDTO> getAllEvents();

    EventDTO addEvent(EventDTO eventDTO) throws Exception;
    void updateEvent(EventDTO eventDTO, Long id) throws ResourceNotFoundException;

    void deleteEventById(Long id);
    void deleteEventByName(String name);

    List<Event> findByPlaceId(Long id);
}
