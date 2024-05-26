package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.service.interfaces.ISeatService;
import com.proyectointegrador.msplace.service.interfaces.IZoneService;
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

    private final IZoneService zoneService;

    @GetMapping("/id/{id}")
    public Optional<ZoneDTO> getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @GetMapping("/name")
    public Optional<ZoneDTO> getZoneByName(@RequestParam("name") String name) {
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteZoneByName(@RequestParam("name") String name) {
        try {
            zoneService.deleteZoneByName(name);
            return ResponseEntity.status(HttpStatus.OK).body("Zona eliminada con éxito: " + name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la zona: " + e.getMessage());
        }
    }
}