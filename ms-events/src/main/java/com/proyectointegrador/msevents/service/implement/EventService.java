package com.proyectointegrador.msevents.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.domain.Place;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.dto.EventGetDTO;
import com.proyectointegrador.msevents.repository.IEventRepository;
import com.proyectointegrador.msevents.repository.PlaceRepository;
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

    private final PlaceRepository placeRepository;

    private final ObjectMapper mapper;

    private EventDTO saveEvent(EventDTO eventDTO) {
        Event event = mapper.convertValue(eventDTO, Event.class);
        eventRepository.save(event);
        return mapper.convertValue(event, EventDTO.class);
    }

    @Override
    public Optional<EventGetDTO> getEventById(Long id) {
        Optional<Event> event = eventRepository.findEventById(id);
        EventGetDTO eventDTO;
        if (event.isPresent()) {
            eventDTO = mapper.convertValue(event, EventGetDTO.class);
            Optional<Place> place = placeRepository.getPlaceById(id);
            place.ifPresent(eventDTO::setPlace);
            return Optional.ofNullable(eventDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<EventGetDTO> getEventByName(String name) {
        Optional<Event> event = eventRepository.findEventByName(name);
        EventGetDTO eventDTO;
        if (event.isPresent()) {
            eventDTO = mapper.convertValue(event, EventGetDTO.class);
            Optional<Place> place = placeRepository.getPlaceById(event.get().getPlaceId());
            place.ifPresent(eventDTO::setPlace);
            return Optional.ofNullable(eventDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Set<EventGetDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        Set<EventGetDTO> eventsDTO = new HashSet<>();
        for (Event event : events) {
            eventsDTO.add(mapper.convertValue(event, EventGetDTO.class));
            for (EventGetDTO eventGetDTO : eventsDTO) {
                Optional<Place> place = placeRepository.getPlaceById(event.getPlaceId());
                place.ifPresent(eventGetDTO::setPlace);
            }
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
