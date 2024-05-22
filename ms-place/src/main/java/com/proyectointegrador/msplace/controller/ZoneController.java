package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.domain.Zone;
import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.service.implement.ZoneService;
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
@RequestMapping("/zone")
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping("/id/{id}")
    public Optional<ZoneDTO> getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<ZoneDTO> getZoneByName(@PathVariable String name) {
        return zoneService.getZoneByName(name);
    }

    @GetMapping("/all")
    public Set<ZoneDTO> getAllZones() {
        return zoneService.getAllZones();
    }

    @GetMapping("/seats/id/{id}")
    public Set<SeatDTO> getSeatsByZoneId(@PathVariable Long id) {
        return zoneService.getSeatsByZoneId(id);
    }

    @GetMapping("/seats/name/{name}")
    public Set<SeatDTO> getSeatsByZoneName(@PathVariable String name) {
        return zoneService.getSeatsByZoneName(name);
    }

    @GetMapping("/seats/availability/{id}")
    public Set<SeatDTO> getSeatsAvailableByZoneId(@PathVariable Long id) {
        return zoneService.getSeatsAvailableByZoneId(id);
    }

    @GetMapping("/seats/noAvailability/{id}")
    public Set<SeatDTO> getSeatsNotAvailableByZoneId(@PathVariable Long id) {
        return zoneService.getSeatsNotAvailableByZoneId(id);
    }
}
