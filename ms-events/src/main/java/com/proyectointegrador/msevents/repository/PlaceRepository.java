package com.proyectointegrador.msevents.repository;

import com.proyectointegrador.msevents.domain.Place;
import com.proyectointegrador.msevents.repository.repository.FeignPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    private final FeignPlaceRepository placeRepository;

    public Optional<Place> getPlaceById(Long id){
        ResponseEntity<Optional<Place>> response = placeRepository.getPlaceById(id);
        return response.getBody();
    }
}
