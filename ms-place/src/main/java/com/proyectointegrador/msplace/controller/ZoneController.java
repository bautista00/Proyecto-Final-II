package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.service.implement.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/availability/{id}")
    public Integer getAvailability(@PathVariable Long id) {
        return zoneService.getAvailability(id);
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

    @PostMapping("/add")
    public ResponseEntity<String> addZone(@RequestBody ZoneDTO zoneDTO) {
        try {
            ZoneDTO zoneDTOR = zoneService.addZone(zoneDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Zona registrada con éxito: " + zoneDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar la zona: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateZone(@RequestBody ZoneDTO zoneDTO) {
        try {
            ZoneDTO zoneDTOR = zoneService.updateZone(zoneDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Zona actualizada con éxito: " + zoneDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la zona: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteZoneById(@PathVariable Long id) {
        try {
            zoneService.deleteZoneById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Zona eliminada con éxito: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la zona: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteZoneByName(@PathVariable String name) {
        try {
            zoneService.deleteZoneByName(name);
            return ResponseEntity.status(HttpStatus.OK).body("Zona eliminada con éxito: " + name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la zona: " + e.getMessage());
        }
    }
}