package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.dto.DateEventDTO;
import com.proyectointegrador.msevents.service.implement.DateEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dateEvent")
public class DateEventController {

    private final DateEventService dateEventService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getDateEventById(@PathVariable Long id) {
        ResponseEntity response = null;
        Optional<DateEventDTO> eventDate = dateEventService.getDateEventById(id);
        if (eventDate.isPresent()) {
            response = new ResponseEntity<>(eventDate, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Event date with id of: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/getByDate/{date}")
    public ResponseEntity<?> getDateEventByDate(@PathVariable Date date) {
        ResponseEntity response = null;
        Optional<DateEventDTO> eventDate = dateEventService.getDateEventByDate(date);
        if (eventDate.isPresent()) {
            response = new ResponseEntity<>(eventDate, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Event date with date of: " + date + " not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllDateEvents() {
        ResponseEntity response = null;
        Set<DateEventDTO> eventDates = dateEventService.getAllDateEvents();
        if (eventDates.isEmpty()) {
            response = new ResponseEntity<>("No event dates", HttpStatus.NO_CONTENT);
        }
        else {
            response = new ResponseEntity<>(eventDates, HttpStatus.OK);
        }
        return response;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDateEvent(@RequestBody DateEventDTO dateEventDTO) {
        try {
            DateEventDTO newEventDate = dateEventService.addDateEvent(dateEventDTO);
            return new ResponseEntity<>("Event date created successfully - " + newEventDate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating an event date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateDateEvent(@RequestBody DateEventDTO dateEventDTO) {
        try {
            DateEventDTO newEventDate = dateEventService.updateDateEvent(dateEventDTO);
            return new ResponseEntity<>("Event date updated successfully - " + newEventDate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating the event date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteDateEventById(@PathVariable Long id) {
        try {
            dateEventService.deleteDateEventById(id);
            return new ResponseEntity<>("Event date with id of: " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting the event date with id of: " + id + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteByDate/{date}")
    public ResponseEntity<?> deleteDateEventByDate(@PathVariable Date date) {
        try {
            dateEventService.deleteDateEventByDate(date);
            return new ResponseEntity<>("Event date with date of: " + date + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting the event date with date of: " + date + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
