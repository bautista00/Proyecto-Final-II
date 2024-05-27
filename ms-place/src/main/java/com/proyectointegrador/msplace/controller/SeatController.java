package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.SeatOnlyDTO;
import com.proyectointegrador.msplace.service.implement.SeatService;
import com.proyectointegrador.msplace.service.interfaces.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/seat")
public class SeatController {

    private final ISeatService seatService;

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

    @GetMapping("/zone/id/{id}")
    public Set<SeatOnlyDTO> getSeatsByZoneId(@PathVariable Long id) {
        return seatService.getAllSeatsByZoneId(id);
    }

    @GetMapping("/zone/name")
    public Set<SeatOnlyDTO> getSeatsByZoneName(@RequestParam("name") String name) {
        return seatService.getSeatsByZoneName(name);
    }

    @GetMapping("/zone/availability/{id}")
    public Set<SeatOnlyDTO> getSeatsAvailableByZoneId(@PathVariable Long id) {
        return seatService.getSeatsAvailableByZoneId(id);
    }

    @GetMapping("/zone/noAvailability/{id}")
    public Set<SeatOnlyDTO> getSeatsNotAvailableByZoneId(@PathVariable Long id) {
        return seatService.getSeatsNotAvailableByZoneId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addSeat(@RequestBody SeatDTO seatDTO) {
        try {
            SeatDTO seatDTOR = seatService.addSeat(seatDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Asiento registrado con éxito: " + seatDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el asiento: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updateZone(@RequestBody SeatDTO seatDTO) {
        try {
            SeatDTO seatDTOR = seatService.updateSeat(seatDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Asiento actualizado con éxito: " + seatDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el asiento: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/availability/{id}")
    public SeatDTO updateAvailability(@PathVariable Long id) {
        return seatService.putAvailability(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long id) {
        try {
            seatService.deleteSeatById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Asiento eliminado con éxito: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el asiento: " + e.getMessage());
        }
    }
}