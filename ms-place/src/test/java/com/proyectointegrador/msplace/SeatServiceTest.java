package com.proyectointegrador.msplace;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplace.domain.Seat;
import com.proyectointegrador.msplace.domain.Zone;
import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.SeatOnlyDTO;
import com.proyectointegrador.msplace.repository.EventRepository;
import com.proyectointegrador.msplace.repository.ISeatRepository;
import com.proyectointegrador.msplace.service.implement.SeatService;
import com.proyectointegrador.msplace.service.implement.ZoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private ISeatRepository seatRepository;

    @Mock
    private ZoneService zoneService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private SeatService seatService;

    private Seat seat;
    private SeatDTO seatDTO;

    @BeforeEach
    void setUp() {
        seat = new Seat();
        seat.setId(1L);
        seat.setAvailability(1);

        seatDTO = new SeatDTO();
        seatDTO.setId(1L);
        seatDTO.setAvailability(1);
    }

    @Test
    void getSeatById_shouldReturnSeatDTO() {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setAvailability(1);
        seat.setPrice(2.0);
        seat.setTicketId(3L);

        when(mapper.convertValue(Optional.of(seat), SeatDTO.class)).thenReturn(seatDTO);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        Optional<SeatDTO> result = seatService.getSeatById(1L);

        assertTrue(result.isPresent());
        assertEquals(seatDTO, result.get());
        verify(seatRepository).findById(1L);
        verify(mapper).convertValue(Optional.of(seat), SeatDTO.class);
    }




    @Test
    void getSeatById_shouldReturnEmptyWhenNotFound() {
        when(seatRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<SeatDTO> result = seatService.getSeatById(1L);

        assertFalse(result.isPresent());
        verify(seatRepository).findById(1L);
    }

    @Test
    void getAllSeats_shouldReturnSeatDTOs() {
        List<Seat> seats = Collections.singletonList(seat);
        when(seatRepository.findAll()).thenReturn(seats);
        when(mapper.convertValue(seat, SeatDTO.class)).thenReturn(seatDTO);

        Set<SeatDTO> result = seatService.getAllSeats();

        assertEquals(1, result.size());
        assertTrue(result.contains(seatDTO));
        verify(seatRepository).findAll();
        verify(mapper, times(1)).convertValue(seat, SeatDTO.class);
    }

    @Test
    void addSeat_shouldSaveAndReturnSeatDTO() {
        when(mapper.convertValue(seatDTO, Seat.class)).thenReturn(seat);
        when(seatRepository.save(seat)).thenReturn(seat);
        when(mapper.convertValue(seat, SeatDTO.class)).thenReturn(seatDTO);

        SeatDTO result = seatService.addSeat(seatDTO);

        assertEquals(seatDTO, result);
        verify(seatRepository).save(seat);
        verify(mapper).convertValue(seatDTO, Seat.class);
        verify(mapper).convertValue(seat, SeatDTO.class);
    }

    @Test
    void updateSeat_shouldUpdateAndReturnSeatDTO() {
        when(mapper.convertValue(seatDTO, Seat.class)).thenReturn(seat);
        when(seatRepository.save(seat)).thenReturn(seat);
        when(mapper.convertValue(seat, SeatDTO.class)).thenReturn(seatDTO);

        SeatDTO result = seatService.updateSeat(seatDTO);

        assertEquals(seatDTO, result);
        verify(seatRepository).save(seat);
        verify(mapper).convertValue(seatDTO, Seat.class);
        verify(mapper).convertValue(seat, SeatDTO.class);
    }

    @Test
    void deleteSeatById_shouldDeleteSeat() {
        doNothing().when(seatRepository).deleteById(1L);

        seatService.deleteSeatById(1L);

        verify(seatRepository).deleteById(1L);
    }

    @Test
    void getAvailability_shouldReturnAvailability() {
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        Integer availability = seatService.getAvailability(1L);

        assertEquals(1, availability);
        verify(seatRepository).findById(1L);
    }

    @Test
    void getSeatsNotAvailableByZoneId_shouldReturnNotAvailableSeats() {
        Zone zone = new Zone();
        zone.setId(1L);

        Seat seat1 = new Seat();
        seat1.setId(1L);
        seat1.setZone(zone);
        seat1.setAvailability(0);

        Seat seat2 = new Seat();
        seat2.setId(2L);
        seat2.setZone(zone);
        seat2.setAvailability(1);

        Seat seat3 = new Seat();
        seat3.setId(3L);
        seat3.setZone(zone);
        seat3.setAvailability(0);

        List<Seat> allSeats = new ArrayList<>();
        allSeats.add(seat1);
        allSeats.add(seat2);
        allSeats.add(seat3);

        // Convertir los seats a SeatOnlyDTOs
        Set<SeatOnlyDTO> allSeatDTOs = allSeats.stream()
                .map(seat -> mapper.convertValue(seat, SeatOnlyDTO.class))
                .collect(Collectors.toSet());

        // Configurar el mock del repository para devolver la lista de seats
        when(seatRepository.findAll()).thenReturn(allSeats);

        // Mockear el método getAllSeatsByZoneId para devolver los DTOs
        when(seatService.getAllSeatsByZoneId(1L)).thenReturn(allSeatDTOs);

        // Ejecutar el método a probar
        Set<SeatOnlyDTO> result = seatService.getSeatsNotAvailableByZoneId(1L);

        // Verificar el resultado
        assertEquals(2, result.size());
        for (SeatOnlyDTO seatDTO : result) {
            assertEquals(0, seatDTO.getAvailability());
        }
    }



    @Test
    void getAvailability_shouldReturnMinusOneWhenSeatNotFound() {
        when(seatRepository.findById(1L)).thenReturn(Optional.empty());

        Integer availability = seatService.getAvailability(1L);

        assertEquals(-1, availability);
        verify(seatRepository).findById(1L);
    }

    @Test
    void putAvailability_shouldToggleAvailability() {
        Seat seat = new Seat();
        Zone zone = new Zone();
        zone.setId(1L);
        seat.setZone(zone);
        seat.setAvailability(1);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        when(seatRepository.save(any())).thenReturn(seat);
        when(mapper.convertValue(any(), eq(SeatDTO.class))).thenReturn(seatDTO);

        SeatDTO result = seatService.putAvailability(1L);

        assertEquals(seatDTO, result);

        verify(seatRepository).findById(1L);
        verify(seatRepository).save(any());
        verify(zoneService).putAvailability(0, zone.getId());
    }



    @Test
    void getAllSeatsByZoneId_shouldReturnSeats() {
        Seat seat = new Seat();
        Zone zone = new Zone();
        zone.setId(1L);
        seat.setZone(zone);

        when(seatRepository.findAll()).thenReturn(Collections.singletonList(seat));
        when(mapper.convertValue(seat, SeatOnlyDTO.class)).thenReturn(new SeatOnlyDTO());

        Set<SeatOnlyDTO> result = seatService.getAllSeatsByZoneId(1L);

        assertEquals(1, result.size());
        verify(seatRepository).findAll();
    }


    @Test
    void getSeatsByZoneName_shouldReturnSeats() {
        Seat seat = new Seat();
        Zone zone = new Zone();
        zone.setName("Zone Name");
        seat.setZone(zone);

        when(seatRepository.findAll()).thenReturn(Collections.singletonList(seat));
        when(mapper.convertValue(seat, SeatOnlyDTO.class)).thenReturn(new SeatOnlyDTO());

        Set<SeatOnlyDTO> result = seatService.getSeatsByZoneName("Zone Name");

        assertEquals(1, result.size());
        verify(seatRepository).findAll();
    }


    @Test
    void getSeatsAvailableByZoneId_shouldReturnAvailableSeats() {
        Seat seat = new Seat();
        seat.setPrice(2.0);
        seat.setId(2L);
        seat.setTicketId(3L);
        seat.setAvailability(4);
        Zone zone = new Zone();
        zone.setId(1L);
        seat.setZone(zone);
        seat.setAvailability(1);

        when(mapper.convertValue(seat, SeatOnlyDTO.class)).thenAnswer(invocation -> {
            Seat seatArgument = invocation.getArgument(0);
            SeatOnlyDTO seatDTO = new SeatOnlyDTO();
            seatDTO.setAvailability(seatArgument.getAvailability());
            return seatDTO;
        });

        when(seatRepository.findAll()).thenReturn(Collections.singletonList(seat));

        Set<SeatOnlyDTO> result = seatService.getSeatsAvailableByZoneId(1L);

        assertEquals(1, result.size());
        verify(seatRepository).findAll();
    }





    @Test
    void findByTicketId_shouldReturnSeats() {
        List<Seat> seats = Collections.singletonList(seat);
        when(seatRepository.findByTicketId(1L)).thenReturn(seats);

        List<Seat> result = seatService.findByTicketId(1L);

        assertEquals(seats, result);
        verify(seatRepository).findByTicketId(1L);
    }

    @Test
    void updateSeatByTicket_shouldUpdateSeat() {
        Seat seat = new Seat();
        seat.setId(1L);
        seat.setTicketId(1L);
        seat.setAvailability(1);

        Zone zone = new Zone();
        zone.setId(1L);
        seat.setZone(zone);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        Optional<Seat> result = seatService.updateSeatByTicket(seat);

        assertTrue(result.isPresent());
        assertEquals(seat.getTicketId(), result.get().getTicketId());

    }


}

