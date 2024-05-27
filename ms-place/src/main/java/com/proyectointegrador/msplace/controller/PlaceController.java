package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.dto.PlaceDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.dto.ZoneOnlyDTO;
import com.proyectointegrador.msplace.service.implement.PlaceService;
import com.proyectointegrador.msplace.service.interfaces.IPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place")
public class PlaceController {

    private final IPlaceService placeService;

    @GetMapping("/id/{id}")
    public Optional<PlaceDTO> getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @GetMapping("/name")
    public Optional<PlaceDTO> getPlaceByName(@RequestParam("name") String name) {
        return placeService.getPlaceByName(name);
    }

    @GetMapping("/all")
    public Set<PlaceDTO> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/zones/{id}")
    public Set<ZoneOnlyDTO> getAllZonesByPlaceId(@PathVariable Long id) {
        return placeService.getAllZonesByPlaceId(id);
    }

    @GetMapping("/zones/name")
    public Set<ZoneOnlyDTO> getAllZonesByPlaceName(@RequestParam("name") String name) {
        return placeService.getAllZonesByPlaceName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> addPlace(@RequestBody PlaceDTO placeDTO) {
        try {
            PlaceDTO placeDTOR = placeService.addPlace(placeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Estadio registrado con éxito: " + placeDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el estadio: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePlace(@RequestBody PlaceDTO placeDTO) {
        try {
            PlaceDTO placeDTOR = placeService.updatePlace(placeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Estadio actualizado con éxito: " + placeDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el estadio: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlaceById(@PathVariable Long id) {
        try {
            placeService.deletePlaceById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Estadio eliminado con éxito: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el estadio: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/name")
    public ResponseEntity<String> deletePlaceByName(@RequestParam("name") String name) {
        try {
            placeService.deletePlaceByName(name);
            return ResponseEntity.status(HttpStatus.OK).body("Estadio eliminado con éxito: " + name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el estadio: " + e.getMessage());
        }
    }
}