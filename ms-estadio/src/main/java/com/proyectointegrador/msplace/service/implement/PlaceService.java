package com.proyectointegrador.msplace.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplace.domain.Place;
import com.proyectointegrador.msplace.dto.PlaceDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.repository.IPlaceRepository;
import com.proyectointegrador.msplace.service.interfaces.IPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlaceService implements IPlaceService {

    private final IPlaceRepository placeRepository;

    private final ZoneService zoneService;

    private final ObjectMapper mapper;

    private PlaceDTO savePlace(PlaceDTO placeDTO) {
        Place place = mapper.convertValue(placeDTO, Place.class);
        placeRepository.save(place);
        return mapper.convertValue(place, PlaceDTO.class);
    }

    @Override
    public Optional<PlaceDTO> getPlaceById(Long id) {
        Optional<Place> place = placeRepository.findById(id);
        PlaceDTO placeDTO = null;
        if (place.isPresent()) {
            placeDTO = mapper.convertValue(place, PlaceDTO.class);
            return Optional.ofNullable(placeDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PlaceDTO> getPlaceByName(String name) {
        Optional<Place> place = placeRepository.findByName(name);
        if (place.isPresent()) {
           PlaceDTO placeDTO = mapper.convertValue(place, PlaceDTO.class);
            return Optional.ofNullable(placeDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<PlaceDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        Set<PlaceDTO> placesDTO = new HashSet<>();
        for (Place place : places) {
            placesDTO.add(mapper.convertValue(place, PlaceDTO.class));
        }
        return placesDTO;
    }

    @Override
    public PlaceDTO addPlace(PlaceDTO placeDTO) {
        return savePlace(placeDTO);
    }

    @Override
    public PlaceDTO updatePlace(PlaceDTO placeDTO) {
        return savePlace(placeDTO);
    }

    @Override
    public void deletePlaceById(Long id) {
        placeRepository.deleteById(id);
    }

    @Override
    public void deletePlaceByName(String name) {
        Optional<Place> place = placeRepository.findByName(name);
        place.ifPresent(placeRepository::delete);
    }

    @Override
    public Set<ZoneDTO> getAllZonesByPlaceId(Long id) {
        return zoneService.getAllZonesByPlaceId(id);
    }

    @Override
    public Set<ZoneDTO> getAllZonesByPlaceName(String name) {
        Optional<PlaceDTO> place = getPlaceByName(name);
        if (place.isPresent()) {
            return zoneService.getAllZonesByPlaceId(place.get().getId());
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public Set<PlaceDTO> getAllPlacesByCityId(Long id) {
        List<Place> places = placeRepository.findAll();
        Set<PlaceDTO> placesDTO = new HashSet<>();
        for (Place place : places) {
            if (place.getCity().getId().equals(id)) {
                placesDTO.add(mapper.convertValue(place, PlaceDTO.class));
            }
        }
        return placesDTO;
    }
}
