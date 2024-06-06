package com.proyectointegrador.msevents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.service.implement.AwsService;
import com.proyectointegrador.msevents.service.implement.EventService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    private final ObjectMapper objectMapper;

    private AwsService awsService;

    @GetMapping("/public/getById/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        ResponseEntity response = null;
        Optional<EventDTO> event = eventService.getEventById(id);
        if (event.isPresent()) {
            response = new ResponseEntity<>(event, HttpStatus.OK);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event with the id of: " + id + " not found.");
        }
        return response;
    }

    @GetMapping("/public/getByName/{name}")
    public ResponseEntity<?> getEventByName(@PathVariable String name) {
        ResponseEntity response = null;
        Optional<EventDTO> event = eventService.getEventByName(name);
        if (event.isPresent()) {
            response = new ResponseEntity<>(event, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>("Event with the name: " + name + " not found", HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/public/get/all")
    public ResponseEntity<?> getAllEvents() {
        ResponseEntity response = null;
        Set<EventDTO> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            response = new ResponseEntity<>("No events", HttpStatus.NO_CONTENT);
        }
        else {
            response = new ResponseEntity<>(events, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping("/findByPlaceId/{id}")
    public ResponseEntity<List<Event>> findByPlaceId(@PathVariable Long id) {
        return ResponseEntity.ok().body(eventService.findByPlaceId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/add")
    public ResponseEntity<?> addEvent(@RequestParam(value="eventDTO") String eventDTOStr,@RequestPart(value = "file") List<MultipartFile> files) throws Exception {

        if (files == null || files.isEmpty()) {
            return new ResponseEntity<>("At least one photo is required", HttpStatus.BAD_REQUEST);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EventDTO eventDTO = objectMapper.readValue(eventDTOStr, EventDTO.class);

        try {
            awsService.uploadFiles(files);
            StringBuilder response = new StringBuilder("The following files were successfully uploaded to the s3 bucket:\n");
            for (MultipartFile file : files) {
                response.append(file.getOriginalFilename()).append("\n");
            }

            EventDTO newEventDTO = eventService.addEvent(eventDTO);
            response.append("Event created successfully - ").append(newEventDTO);

            return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while creating an event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/private/update")
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO,@RequestParam(value = "id") Long id) {
        try {
            eventService.updateEvent(eventDTO, id);
            return ResponseEntity.ok("Event updated successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Error while updating event: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/deleteById/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable Long id) {
        try {
            eventService.deleteEventById(id);
            return new ResponseEntity<>("Event deleted with the id: " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting event with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/deleteByName/{name}")
    public ResponseEntity<?> deleteEventByName(@PathVariable String name) {
        try {
            eventService.deleteEventByName(name);
            return new ResponseEntity<>("Event deleted with the name: " + name + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while deleting event with the name: " + name, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
