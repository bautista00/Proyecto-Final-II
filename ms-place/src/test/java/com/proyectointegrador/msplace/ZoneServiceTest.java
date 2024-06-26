package com.proyectointegrador.msplace;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplace.domain.Seat;
import com.proyectointegrador.msplace.domain.Zone;
import com.proyectointegrador.msplace.dto.SeatOnlyDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.repository.IZoneRepository;
import com.proyectointegrador.msplace.service.implement.ZoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ZoneServiceTest {

    @Mock
    private IZoneRepository zoneRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private ZoneService zoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetZoneById_WhenZoneExists() {

        Long id = 1L;
        Zone zone = new Zone();
        zone.setId(id);
        zone.setName("Test Zone");
        Set<Seat> seats = new HashSet<>();
        Seat seat1 = new Seat();
        seat1.setId(1L);
        Seat seat2 = new Seat();
        seat2.setId(2L);
        seats.add(seat1);
        seats.add(seat2);
        zone.setSeats(seats);
        when(zoneRepository.findById(id)).thenReturn(Optional.of(zone));
        when(mapper.convertValue(zone, ZoneDTO.class)).thenReturn(new ZoneDTO());
        when(mapper.convertValue(any(Seat.class), eq(SeatOnlyDTO.class))).thenReturn(new SeatOnlyDTO());


        Optional<ZoneDTO> zoneDTOOptional = zoneService.getZoneById(id);


        assertTrue(zoneDTOOptional.isPresent());
        assertEquals(1, zoneDTOOptional.get().getSeats().size());
    }

    @Test
    void testGetZoneById_WhenZoneDoesNotExist() {

        Long id = 1L;
        when(zoneRepository.findById(id)).thenReturn(Optional.empty());


        Optional<ZoneDTO> zoneDTOOptional = zoneService.getZoneById(id);

        assertFalse(zoneDTOOptional.isPresent());
    }

    @Test
    void testGetZoneByName_WhenZoneExists() {
        String name = "Test Zone";
        Zone zone = new Zone();
        zone.setId(1L);
        zone.setName(name);
        Set<Seat> seats = new HashSet<>();
        Seat seat1 = new Seat();
        seat1.setId(1L);
        Seat seat2 = new Seat();
        seat2.setId(2L);
        seats.add(seat1);
        seats.add(seat2);
        zone.setSeats(seats);
        when(zoneRepository.findByName(name)).thenReturn(Optional.of(zone));
        when(mapper.convertValue(zone, ZoneDTO.class)).thenReturn(new ZoneDTO());
        when(mapper.convertValue(any(Seat.class), eq(SeatOnlyDTO.class))).thenReturn(new SeatOnlyDTO());

        Optional<ZoneDTO> zoneDTOOptional = zoneService.getZoneByName(name);

        assertTrue(zoneDTOOptional.isPresent());
        assertEquals(1, zoneDTOOptional.get().getSeats().size());
    }

    @Test
    void testGetZoneByName_WhenZoneDoesNotExist() {
        String name = "Test Zone";
        when(zoneRepository.findByName(name)).thenReturn(Optional.empty());

        Optional<ZoneDTO> zoneDTOOptional = zoneService.getZoneByName(name);
        assertFalse(zoneDTOOptional.isPresent());
    }



}

