package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.domain.City;
import com.proyectointegrador.msplace.dto.CityDTO;
import com.proyectointegrador.msplace.dto.PlaceDTO;
import com.proyectointegrador.msplace.service.implement.CityService;
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
@RequestMapping("/city")
public class CityController {

    private final CityService cityService;

    @GetMapping("/id/{id}")
    public Optional<CityDTO> getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<CityDTO> getCityByName(@PathVariable String name) {
        return cityService.getCityByName(name);
    }

    @GetMapping("/all")
    public Set<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/zipCode/{zipCode}")
    public Set<CityDTO> getCityByZipCode(@PathVariable String zipCode) {
        return cityService.getCityByZipCode(zipCode);
    }

    @GetMapping("/places/{id}")
    public Set<PlaceDTO> getAllPlacesByCityId(@PathVariable Long id) {
        return cityService.getAllPlacesByCityId(id);
    }

    @GetMapping("/places/{name}")
    public Set<PlaceDTO> getAllPlacesByCityName(@PathVariable String name) {
        return cityService.getAllPlacesByCityName(name);
    }
}

/*
CityDTO addCity(CityDTO cityDTO);
    CityDTO updateCity(CityDTO cityDTO);
    void deleteCityById(Long id);
    void deleteCityByName(String name);
 */