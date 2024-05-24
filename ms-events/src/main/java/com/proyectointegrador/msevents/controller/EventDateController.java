package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.EventDate;
import com.proyectointegrador.msevents.dto.EventDateDTO;
import com.proyectointegrador.msevents.service.implement.EventDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/eventDate")
public class EventDateController {

    private final EventDateService eventDateService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getEventDateById(@PathVariable Long id) {
        ResponseEntity response = null;
        Optional<EventDateDTO> eventDate = eventDateService.getEventDateById(id);
        if (eventDate.isPresent()) {
            response = new ResponseEntity<>(eventDate, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Event date with id of: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<?> getEventDateById(@PathVariable Date date) {
        ResponseEntity response = null;
        Optional<EventDateDTO> eventDate = eventDateService.getEventDateByDate(date);
        if (eventDate.isPresent()) {
            response = new ResponseEntity<>(eventDate, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Event date with date of: " + date + " not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllEventDates() {
        ResponseEntity response = null;
        Set<EventDateDTO> eventDates = eventDateService.getAllEventDates();
        if (eventDates.isEmpty()) {
            response = new ResponseEntity<>("No event dates", HttpStatus.NO_CONTENT);
        }
        else {
            response = new ResponseEntity<>(eventDates, HttpStatus.OK);
        }
        return response;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEventDate(@RequestBody EventDateDTO eventDateDTO) {
        try {
            EventDateDTO newEventDate = eventDateService.addEventDate(eventDateDTO);
            return new ResponseEntity<>("Event date created successfully " + newEventDate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating an event date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEventDate(@RequestBody EventDateDTO eventDateDTO) {
        try {
            EventDateDTO newEventDate = eventDateService.updateEventDate(eventDateDTO);
            return new ResponseEntity<>("Event date updated successfully " + newEventDate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating the event date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteEventDateById(@PathVariable Long id) {
        try {
            eventDateService.deleteDateEventById(id);
            return new ResponseEntity<>("Event date with id of: " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting the event date with id of: " + id + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteByDate/{date}")
    public ResponseEntity<?> deleteEventDateByDate(@PathVariable Date date) {
        try {
            eventDateService.deleteDateEventByDate(date);
            return new ResponseEntity<>("Event date with date of: " + date + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting the event date with date of: " + date + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
