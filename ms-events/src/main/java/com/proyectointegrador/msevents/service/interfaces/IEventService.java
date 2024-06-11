package com.proyectointegrador.msevents.service.interfaces;


import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEventService {

    Optional<EventDTO> getEventById(Long id);
    Optional<EventDTO> getEventByName(String name);

    Set<EventDTO> getAllEvents();

    EventDTO addEvent(EventDTO eventDTO,List<MultipartFile> files) throws Exception;
    void updateEvent(EventDTO eventDTO, Long id) throws ResourceNotFoundException;

    void deleteEventById(Long id);
    void deleteEventByName(String name);

    List<Event> findByPlaceId(Long id);
}
