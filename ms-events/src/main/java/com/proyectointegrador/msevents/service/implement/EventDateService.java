package com.proyectointegrador.msevents.service.implement;

import com.proyectointegrador.msevents.domain.EventDate;
import com.proyectointegrador.msevents.repository.IEventDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventDateService {

    @Autowired
    private IEventDateRepository eventDateRepository;

    public Optional<EventDate> getEventDateById(Long id) {
        return eventDateRepository.findById(id);
    }

    public List<EventDate> getAllEventDates() {
        return eventDateRepository.findAll();
    }
}
