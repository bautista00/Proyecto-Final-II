package com.proyectointegrador.msevents.controller;

import com.proyectointegrador.msevents.domain.EventDate;
import com.proyectointegrador.msevents.service.implement.EventDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fechaEvento")
public class EventDateController {

    @Autowired
    private EventDateService eventDateService;

    @GetMapping("/all")
    public List<EventDate> getAllEventDates() {
        return eventDateService.getAllEventDates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getEventDateById(@PathVariable Long id) {
        ResponseEntity response = null;
        if (eventDateService.getEventDateById(id).isEmpty()) {
            response = new ResponseEntity<>("Event date not found", HttpStatus.NOT_FOUND);
        }
        else {
            response = new ResponseEntity<>(eventDateService.getEventDateById(id), HttpStatus.OK);
        }

        return response;
    }
}
