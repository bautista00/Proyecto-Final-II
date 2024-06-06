package com.proyectointegrador.msevents.service.implement;

import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.DateEvent;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.domain.Images;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.repository.IDateEventRepository;
import com.proyectointegrador.msevents.repository.IEventRepository;
import com.proyectointegrador.msevents.repository.IImagesRepository;
import com.proyectointegrador.msevents.service.interfaces.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {

    private final IEventRepository eventRepository;
    private final IDateEventRepository dateEventRepository;
    private final AwsService awsService;
    private final IImagesRepository imagesRepository;

    private final ObjectMapper mapper;

    @Transactional
    private EventDTO saveEvent(EventDTO eventDTO, List<MultipartFile> files) {
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
    public void updateEvent(EventDTO eventDTO, Long id) throws ResourceNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findEventById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventDTO.getName());
            event.setDescription(eventDTO.getDescription());
            event.setImages(eventDTO.getImages());
            event.setCategory(eventDTO.getCategory());
            event.setDateEvent(eventDTO.getDateEvent());
            event.setPlaceId(eventDTO.getPlaceId());
            eventRepository.save(event);
        }
        else {
            throw new ResourceNotFoundException("Event not found with id: " + id);
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
