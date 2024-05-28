package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.dto.DateEventDTO;
import com.proyectointegrador.msevents.service.implement.DateEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dateEvent")
public class DateEventController {

    private final DateEventService dateEventService;

    @GetMapping("/public/getById/{id}")
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

    @GetMapping("/public/getByDate/{date}")
    public ResponseEntity<?> getDateEventByDate(@PathVariable String date) {
        ResponseEntity response = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date dateObj = dateFormat.parse(date);
            Optional<DateEventDTO> eventDate = dateEventService.getDateEventByDate(dateObj);
            if (eventDate.isPresent()) {
                response = new ResponseEntity<>(eventDate, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>("Event date with date of: " + dateObj + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (ParseException e) {
            response = new ResponseEntity<>("Invalid date format: " + date, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @GetMapping("/public/get/all")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/add")
    public ResponseEntity<?> addDateEvent(@RequestBody DateEventDTO dateEventDTO) {
        try {
            DateEventDTO newEventDate = dateEventService.addDateEvent(dateEventDTO);
            return new ResponseEntity<>("Event date created successfully - " + newEventDate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating an event date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/private/update")
    public ResponseEntity<?> updateDateEvent(@RequestBody DateEventDTO dateEventDTO) {
        try {
            DateEventDTO newEventDate = dateEventService.updateDateEvent(dateEventDTO);
            return new ResponseEntity<>("Event date updated successfully - " + newEventDate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating the event date: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/deleteById/{id}")
    public ResponseEntity<?> deleteDateEventById(@PathVariable Long id) {
        try {
            dateEventService.deleteDateEventById(id);
            return new ResponseEntity<>("Event date with id of: " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting the event date with id of: " + id + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/deleteByDate/{dateStr}")
    public ResponseEntity<?> deleteDateEventByDate(@PathVariable String dateStr) {
        ResponseEntity response = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date dateObj = dateFormat.parse(dateStr);
            Optional<DateEventDTO> eventDate = dateEventService.getDateEventByDate(dateObj);
            if (eventDate.isPresent()) {
                dateEventService.deleteDateEventByDate(dateObj);
                response = new ResponseEntity<>(eventDate, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>("Event date with date of: " + dateObj + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (ParseException e) {
            response = new ResponseEntity<>("Invalid date format: " + dateStr, HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
