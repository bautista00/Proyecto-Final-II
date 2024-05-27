package com.proyectointegrador.msplace.service.interfaces;

import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.SeatOnlyDTO;
import java.util.Optional;
import java.util.Set;

public interface ISeatService {
    Optional<SeatDTO> getSeatById(Long id);
    Set<SeatDTO> getAllSeats();
    SeatDTO addSeat(SeatDTO seatDTO);
    SeatDTO updateSeat(SeatDTO seatDTO);
    void deleteSeatById(Long id);
    Integer getAvailability(Long id);
    SeatDTO putAvailability(Long id);
    Set<SeatOnlyDTO> getAllSeatsByZoneId(Long id);
    Set<SeatOnlyDTO> getSeatsByZoneName(String name);
    Set<SeatOnlyDTO> getSeatsAvailableByZoneId(Long id);
    Set<SeatOnlyDTO> getSeatsNotAvailableByZoneId(Long id);
}