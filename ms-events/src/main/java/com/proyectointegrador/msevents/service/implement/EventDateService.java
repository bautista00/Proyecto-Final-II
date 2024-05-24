package com.proyectointegrador.msevents.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.EventDate;
import com.proyectointegrador.msevents.dto.EventDateDTO;
import com.proyectointegrador.msevents.repository.IEventDateRepository;
import com.proyectointegrador.msevents.service.interfaces.IEventDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class EventDateService implements IEventDateService {

    private final IEventDateRepository eventDateRepository;

    private final ObjectMapper mapper;

    private EventDateDTO saveEventDate(EventDateDTO eventDateDTO) {
        EventDate eventDate = mapper.convertValue(eventDateDTO, EventDate.class);
        eventDateRepository.save(eventDate);
        return mapper.convertValue(eventDate, EventDateDTO.class);
    }

    @Override
    public Optional<EventDateDTO> getEventDateById(Long id) {
        Optional<EventDate> eventDate = eventDateRepository.findEventDateById(id);
        EventDateDTO eventDateDTO = null;
        if (eventDate.isPresent()) {
            eventDateDTO = mapper.convertValue(eventDate, EventDateDTO.class);
            return Optional.ofNullable(eventDateDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<EventDateDTO> getEventDateByDate(Date date) {
        Optional<EventDate> eventDate = eventDateRepository.findEventDateByDate(date);
        EventDateDTO eventDateDTO = null;
        if (eventDate.isPresent()) {
            eventDateDTO = mapper.convertValue(eventDate, EventDateDTO.class);
            return Optional.ofNullable(eventDateDTO);
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public Set<EventDateDTO> getAllEventDates() {
        List<EventDate> eventDates = eventDateRepository.findAll();
        Set<EventDateDTO> eventDatesDTO = new HashSet<>();
        for (EventDate eventDate : eventDates) {
            eventDatesDTO.add(mapper.convertValue(eventDate, EventDateDTO.class));
        }
        return eventDatesDTO;
    }

    @Override
    public EventDateDTO addEventDate(EventDateDTO eventDateDTO) {
        return saveEventDate(eventDateDTO);
    }

    @Override
    public EventDateDTO updateEventDate(EventDateDTO eventDateDTO) {
        return saveEventDate(eventDateDTO);
    }

    @Override
    public void deleteDateEventById(Long id) {
        eventDateRepository.deleteById(id);
    }

    @Override
    public void deleteDateEventByDate(Date date) {
        Optional<EventDate> eventDate = eventDateRepository.findEventDateByDate(date);
        eventDate.ifPresent(eventDateRepository::delete);
    }
}
