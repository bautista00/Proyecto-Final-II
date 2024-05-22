package com.proyectointegrador.msplace.controller;

import com.proyectointegrador.msplace.domain.Place;
import com.proyectointegrador.msplace.dto.CityDTO;
import com.proyectointegrador.msplace.dto.PlaceDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.service.implement.PlaceService;
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
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/id/{id}")
    public Optional<PlaceDTO> getPlaceById(@PathVariable Long id) {
        return placeService.getPlaceById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<PlaceDTO> getPlaceByName(@PathVariable String name) {
        return placeService.getPlaceByName(name);
    }

    @GetMapping("/all")
    public Set<PlaceDTO> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/zones/{id}")
    public Set<ZoneDTO> getAllZonesByPlaceId(@PathVariable Long id) {
        return placeService.getAllZonesByPlaceId(id);
    }

    @GetMapping("/zones/{name}")
    public Set<ZoneDTO> getAllZonesByPlaceName(@PathVariable String name) {
        return placeService.getAllZonesByPlaceName(name);
    }


}

/*
PlaceDTO addPlace(PlaceDTO placeDTO);
    PlaceDTO updatePlace(PlaceDTO placeDTO);
    void deletePlaceById(Long id);
    void deletePlaceByName(String name);
 */