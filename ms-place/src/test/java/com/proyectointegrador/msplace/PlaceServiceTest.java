package com.proyectointegrador.msplace;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplace.domain.Event;
import com.proyectointegrador.msplace.domain.Place;
import com.proyectointegrador.msplace.domain.Seat;
import com.proyectointegrador.msplace.domain.Zone;
import com.proyectointegrador.msplace.dto.*;
import com.proyectointegrador.msplace.repository.EventRepository;
import com.proyectointegrador.msplace.repository.IPlaceRepository;
import com.proyectointegrador.msplace.service.implement.PlaceService;
import com.proyectointegrador.msplace.service.implement.ZoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private IPlaceRepository placeRepository;

    @Mock
    private ZoneService zoneService;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private PlaceService placeService;

    private Place place;
    private Seat seat;
    private PlaceDTO placeDTO;

    @BeforeEach
    void setUp() {
        place = new Place();
        place.setId(1L);
        place.setName("Place Name");

        placeDTO = new PlaceDTO();
        placeDTO.setId(1L);
        placeDTO.setName("Place Name");
    }

    @Test
    void getPlaceById_shouldReturnPlaceDTO() {
        // Arrange
        Place place = new Place();
        place.setId(1L);
        place.setName("TestPlace");
        place.setZones(new HashSet<>());

        when(placeRepository.findById(1L)).thenReturn(Optional.of(place));
        when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);
        when(eventRepository.findByPLaceId(1L)).thenReturn(new ArrayList<>());

        // Act
        Optional<PlaceDTO> result = placeService.getPlaceById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(placeDTO, result.get());
        verify(placeRepository).findById(1L);
        verify(eventRepository).findByPLaceId(1L);
        verify(mapper).convertValue(place, PlaceDTO.class);
    }


    @Test
    void getPlaceById_shouldReturnEmptyWhenNotFound() {
        when(placeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<PlaceDTO> result = placeService.getPlaceById(1L);

        assertFalse(result.isPresent());
        verify(placeRepository).findById(1L);
    }

    @Test
    void testGetPlaceByName_WhenPlaceExists() {
        Place place = new Place();
        place.setId(1L);
        place.setName("TestPlace");
        place.setZones(new HashSet<>());

        when(placeRepository.findByName("TestPlace")).thenReturn(Optional.of(place));
        when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);
        when(eventRepository.findByPLaceId(1L)).thenReturn(new ArrayList<>());

        // Act
        Optional<PlaceDTO> placeDTOOptional = placeService.getPlaceByName("TestPlace");

        // Assert
        assertTrue(placeDTOOptional.isPresent());
        PlaceDTO placeDTO = placeDTOOptional.get();
        assertNotNull(placeDTO);
    }




    @Test
    void getPlaceByName_shouldReturnEmptyWhenNotFound() {
        when(placeRepository.findByName("Place Name")).thenReturn(Optional.empty());

        Optional<PlaceDTO> result = placeService.getPlaceByName("Place Name");

        assertFalse(result.isPresent());
        verify(placeRepository).findByName("Place Name");
    }

    @Test
    void getAllPlaces_shouldReturnPlaceDTOs() {
        // Arrange
        Place place = new Place();
        place.setId(1L);
        place.setName("TestPlace");
        place.setZones(new HashSet<>());

        List<Place> places = Collections.singletonList(place);
        when(placeRepository.findAll()).thenReturn(places);
        when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);
        when(eventRepository.findByPLaceId(1L)).thenReturn(new ArrayList<>());

        // Act
        Set<PlaceDTO> result = placeService.getAllPlaces();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(placeDTO));
        verify(placeRepository).findAll();
        verify(eventRepository).findByPLaceId(1L);
        verify(mapper).convertValue(place, PlaceDTO.class);
    }


    @Test
    void addPlace_shouldSaveAndReturnPlaceDTO() {
        when(mapper.convertValue(placeDTO, Place.class)).thenReturn(place);
        when(placeRepository.save(place)).thenReturn(place);
        when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);

        PlaceDTO result = placeService.addPlace(placeDTO);

        assertEquals(placeDTO, result);
        verify(placeRepository).save(place);
        verify(mapper).convertValue(placeDTO, Place.class);
        verify(mapper).convertValue(place, PlaceDTO.class);
    }

    @Test
    void updatePlace_shouldUpdateAndReturnPlaceDTO() {
        when(mapper.convertValue(placeDTO, Place.class)).thenReturn(place);
        when(placeRepository.save(place)).thenReturn(place);
        when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);

        PlaceDTO result = placeService.updatePlace(placeDTO);

        assertEquals(placeDTO, result);
        verify(placeRepository).save(place);
        verify(mapper).convertValue(placeDTO, Place.class);
        verify(mapper).convertValue(place, PlaceDTO.class);
    }

    @Test
    void deletePlaceById_shouldDeletePlace() {
        doNothing().when(placeRepository).deleteById(1L);

        placeService.deletePlaceById(1L);

        verify(placeRepository).deleteById(1L);
    }

    @Test
    void deletePlaceByName_shouldDeletePlace() {
        when(placeRepository.findByName("Place Name")).thenReturn(Optional.of(place));
        doNothing().when(placeRepository).delete(place);

        placeService.deletePlaceByName("Place Name");

        verify(placeRepository).findByName("Place Name");
        verify(placeRepository).delete(place);
    }

    @Test
    void getAllZonesByPlaceId_shouldReturnZones() {
        Set<ZoneOnlyDTO> zones = new HashSet<>();
        when(zoneService.getAllZonesByPlaceId(1L)).thenReturn(zones);

        Set<ZoneOnlyDTO> result = placeService.getAllZonesByPlaceId(1L);

        assertEquals(zones, result);
        verify(zoneService).getAllZonesByPlaceId(1L);
    }

    @Test
    void getAllZonesByPlaceName_shouldReturnZones() {
        Place place = new Place();
        place.setId(1L);
        place.setName("Place Name");
        place.setZones(new HashSet<>());
        Set<ZoneOnlyDTO> zones = new HashSet<>();
        when(placeRepository.findByName("Place Name")).thenReturn(Optional.of(place));
        when(zoneService.getAllZonesByPlaceId(1L)).thenReturn(zones);
        when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);

        Set<ZoneOnlyDTO> result = placeService.getAllZonesByPlaceName("Place Name");

        // Assert
        assertEquals(zones, result);
        verify(placeRepository).findByName("Place Name");
        verify(zoneService).getAllZonesByPlaceId(1L);
    }

}
