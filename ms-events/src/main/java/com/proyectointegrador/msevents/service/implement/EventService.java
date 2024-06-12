package com.proyectointegrador.msevents.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.DateEvent;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.domain.Place;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.dto.EventGetDTO;
import com.proyectointegrador.msevents.repository.IDateEventRepository;
import com.proyectointegrador.msevents.repository.IEventRepository;
import com.proyectointegrador.msevents.repository.PlaceRepository;
import com.proyectointegrador.msevents.service.interfaces.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    private final IDateEventRepository dateEventRepository;

    private final PlaceRepository placeRepository;

    private final ObjectMapper mapper;

    @Transactional
    private EventDTO saveEvent(EventDTO eventDTO) {
        DateEvent dateEvent = eventDTO.getDateEvent();
        if (dateEvent == null) {
            throw new IllegalArgumentException("DateEvent is required");
        }
        dateEventRepository.save(dateEvent);

        Event event = mapper.convertValue(eventDTO, Event.class);
        event.setDateEvent(dateEvent);
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

        // Extraer los IDs de los lugares
        Set<Long> placeIds = events.stream()
                .map(Event::getPlaceId)
                .collect(Collectors.toSet());

        // Obtener todos los lugares en una sola llamada
        List<Place> places = placeRepository.getPlacesByIds(new ArrayList<>(placeIds));

        // Crear un mapa de lugar por ID para fácil acceso
        Map<Long, Place> placeMap = places.stream()
                .collect(Collectors.toMap(Place::getId, Function.identity()));

        // Convertir los eventos a DTO y asignar los lugares
        Set<EventGetDTO> eventsDTO = new HashSet<>();
        for (Event event : events) {
            EventGetDTO eventGetDTO = mapper.convertValue(event, EventGetDTO.class);
            eventGetDTO.setPlace(placeMap.get(event.getPlaceId()));
            eventsDTO.add(eventGetDTO);
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
