package com.proyectointegrador.msevents.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.configuration.Search.EventSpecification;
import com.proyectointegrador.msevents.domain.DateEvent;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.domain.Images;
import com.proyectointegrador.msevents.domain.Place;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.dto.EventGetDTO;
import com.proyectointegrador.msevents.exceptions.ResourceNotFoundException;
import com.proyectointegrador.msevents.repository.IDateEventRepository;
import com.proyectointegrador.msevents.repository.IEventRepository;
import com.proyectointegrador.msevents.repository.IImagesRepository;
import com.proyectointegrador.msevents.repository.PlaceRepository;
import com.proyectointegrador.msevents.service.interfaces.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository eventRepository;

    private final IDateEventRepository dateEventRepository;

    private final PlaceRepository placeRepository;

    private final IImagesRepository imagesRepository;

    private final AwsService awsService;

    private final ObjectMapper mapper;

    @Transactional
    protected EventDTO saveEvent(EventDTO eventDTO, List<MultipartFile> files) throws Exception {
        DateEvent dateEvent = eventDTO.getDateEvent();
        if (dateEvent == null) {
            throw new IllegalArgumentException("DateEvent is required");
        }
        dateEventRepository.save(dateEvent);
        List<String> imageUrls = awsService.generateImageUrls(awsService.uploadFiles(files));
        Images images = new Images();
        images.setUrl(imageUrls);
        imagesRepository.save(images);
        eventDTO.setImages(images);
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
        Set<Long> placeIds = events.stream()
                .map(Event::getPlaceId)
                .collect(Collectors.toSet());
        List<Place> places = placeRepository.getPlacesByIds(new ArrayList<>(placeIds));
        Map<Long, Place> placeMap = places.stream()
                .collect(Collectors.toMap(Place::getId, Function.identity()));
        Set<EventGetDTO> eventsDTO = new HashSet<>();
        for (Event event : events) {
            EventGetDTO eventGetDTO = mapper.convertValue(event, EventGetDTO.class);
            eventGetDTO.setPlace(placeMap.get(event.getPlaceId()));
            eventsDTO.add(eventGetDTO);
        }
        return eventsDTO;
    }

    @Override
    public List<Event> searchEvents(String name, String category, String city, Date date) {
        Specification<Event> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and(EventSpecification.nameContains(name));
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and(EventSpecification.categoryContains(category));
        }
        if (city != null && !city.isEmpty()) {
            spec = spec.and(EventSpecification.cityContains(city));
        }
        if (date != null) {
            spec = spec.and(EventSpecification.dateIs(date));
        }

        return eventRepository.findAll(spec);
    }

    @Override
    public EventDTO addEvent(EventDTO eventDTO, List<MultipartFile> files) throws Exception {
        return saveEvent(eventDTO, files);
    }

    @Override
    public EventDTO updateEvent(EventDTO eventDTO) throws ResourceNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findEventById(eventDTO.getId());
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventDTO.getName());
            event.setDescription(eventDTO.getDescription());
            event.setImages(eventDTO.getImages());
            event.setCategory(eventDTO.getCategory());
            event.setDateEvent(eventDTO.getDateEvent());
            event.setPlaceId(eventDTO.getPlaceId());
            Event eventUpdated = eventRepository.save(event);
            return mapper.convertValue(eventUpdated, EventDTO.class);
        }
        else {
            throw new ResourceNotFoundException("Event not found with id: " + eventDTO.getId());
        }
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
