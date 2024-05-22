package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.domain.Seat;
import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.service.implement.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/id/{id}")
    public Optional<SeatDTO> getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id);
    }

    @GetMapping("/all")
    public Set<SeatDTO> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/availability/{id}")
    public Integer getAvailability(@PathVariable Long id) {
        return seatService.getAvailability(id);
    }
}
