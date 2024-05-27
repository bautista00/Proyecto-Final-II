package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.dto.CityDTO;
import com.proyectointegrador.msplace.dto.PlaceOnlyDTO;
import com.proyectointegrador.msplace.service.interfaces.ICityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/city")
public class CityController {

    private final ICityService cityService;

    @GetMapping("/public/id/{id}")
    public Optional<CityDTO> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/public/name")
    public Optional<CityDTO> getCityByName(@RequestParam("name") String name) {
        return cityService.getCityByName(name);
    }

    @GetMapping("/public/all")
    public Set<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/public/zipCode/{zipCode}")
    public Set<CityDTO> getCityByZipCode(@PathVariable String zipCode) {
        return cityService.getCityByZipCode(zipCode);
    }

    @GetMapping("/public/places/{id}")
    public Set<PlaceOnlyDTO> getAllPlacesByCityId(@PathVariable Long id) {
        return cityService.getAllPlacesByCityId(id);
    }

    @GetMapping("/public/places/name")
    public Set<PlaceOnlyDTO> getAllPlacesByCityName(@RequestParam("name") String name) {
        return cityService.getAllPlacesByCityName(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/add")
    public ResponseEntity<String> addCity(@RequestBody CityDTO cityDTO) {
        try {
            CityDTO cityDTOR = cityService.addCity(cityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ciudad creada con éxito: " + cityDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la ciudad: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/private/update")
    public ResponseEntity<String> updateCity(@RequestBody CityDTO cityDTO) {
        try {
            CityDTO cityDTOR = cityService.updateCity(cityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ciudad actualizada con éxito: " + cityDTOR);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la ciudad: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<String> deleteCityById(@PathVariable Long id) {
        try {
            cityService.deleteCityById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Ciudad eliminada con éxito: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la ciudad: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/private/delete/name")
    public ResponseEntity<String> deleteCityByName(@RequestParam("name") String name) {
        try {
            cityService.deleteCityByName(name);
            return ResponseEntity.status(HttpStatus.OK).body("Ciudad eliminada con éxito: " + name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la ciudad: " + e.getMessage());
        }
    }
}