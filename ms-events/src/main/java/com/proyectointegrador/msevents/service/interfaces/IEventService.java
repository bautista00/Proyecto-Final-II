package com.proyectointegrador.msevents.service.interfaces;

import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.dto.EventGetDTO;
import com.proyectointegrador.msevents.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IEventService {

    Optional<EventGetDTO> getEventById(Long id);
    Optional<EventGetDTO> getEventByName(String name);
    Set<EventGetDTO> getAllEvents();
    List<Event> searchEvents(String name, String category, String city, Date date);

    EventDTO addEvent(EventDTO eventDTO, MultipartFile file) throws Exception;
    EventDTO updateEvent(EventDTO eventDTO) throws ResourceNotFoundException;

    void deleteEventById(Long id);
    void deleteEventByName(String name);

    List<Event> findByPlaceId(Long id);
}
