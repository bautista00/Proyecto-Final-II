package com.proyectointegrador.msevents.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.repository.IEventRepository;
import com.proyectointegrador.msevents.service.interfaces.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    private final ObjectMapper mapper;

    private EventDTO saveEvent(EventDTO eventDTO) {
        Event event = mapper.convertValue(eventDTO, Event.class);
        eventRepository.save(event);
        return mapper.convertValue(event, EventDTO.class);
    }

    @Override
    public Optional<EventDTO> getEventById(Long id) {
        Optional<Event> event = eventRepository.findEventById(id);
        EventDTO eventDTO = null;
        if (event.isPresent()) {
            eventDTO = mapper.convertValue(event, EventDTO.class);
            return Optional.ofNullable(eventDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<EventDTO> getEventByName(String name) {
        Optional<Event> event = eventRepository.findEventByName(name);
        EventDTO eventDTO = null;
        if (event.isPresent()) {
            eventDTO = mapper.convertValue(event, EventDTO.class);
            return Optional.ofNullable(eventDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Set<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        Set<EventDTO> eventsDTO = new HashSet<>();
        for (Event event : events) {
            eventsDTO.add(mapper.convertValue(event, EventDTO.class));
        }
        return eventsDTO;
    }

    @Override
    public EventDTO addEvent(EventDTO eventDTO) {
        return saveEvent(eventDTO);
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) {
        return saveEvent(eventDTO);
    }

    @Override
    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public void deleteEventByName(String name) {
        Optional<Event> event = eventRepository.findEventByName(name);
        event.ifPresent(eventRepository::delete);
    }

    @Override
    public List<Event> findByPlaceId(Long id) {
        return eventRepository.findByPlaceId(id);
    }
}
