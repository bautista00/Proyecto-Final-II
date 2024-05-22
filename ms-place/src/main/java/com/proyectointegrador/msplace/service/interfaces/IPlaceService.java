package com.proyectointegrador.msplace.service.interfaces;

import com.proyectointegrador.msplace.dto.PlaceDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import java.util.Optional;
import java.util.Set;

public interface IPlaceService {
    Optional<PlaceDTO> getPlaceById(Long id);
    Optional<PlaceDTO> getPlaceByName(String name);
    Set<PlaceDTO> getAllPlaces();
    PlaceDTO addPlace(PlaceDTO placeDTO);
    PlaceDTO updatePlace(PlaceDTO placeDTO);
    void deletePlaceById(Long id);
    void deletePlaceByName(String name);
    Set<ZoneDTO> getAllZonesByPlaceId(Long id);
    Set<ZoneDTO> getAllZonesByPlaceName(String name);
    Set<PlaceDTO> getAllPlacesByCityId(Long id);
}
