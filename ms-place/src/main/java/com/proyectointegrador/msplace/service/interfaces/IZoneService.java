package com.proyectointegrador.msplace.service.interfaces;

import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import java.util.Optional;
import java.util.Set;

public interface IZoneService {
    Optional<ZoneDTO> getZoneById(Long id);
    Optional<ZoneDTO> getZoneByName(String name);
    Set<ZoneDTO> getAllZones();
    ZoneDTO addZone(ZoneDTO zoneDTO);
    ZoneDTO updateZone(ZoneDTO zoneDTO);
    void deleteZoneById(Long id);
    void deleteZoneByName(String name);
    Integer getAvailability(Long id);
    void putAvailability(Integer number, Long id);
    Set<SeatDTO> getSeatsByZoneId(Long id);
    Set<SeatDTO> getSeatsByZoneName(String name);
    Set<SeatDTO> getSeatsAvailableByZoneId(Long id);
    Set<SeatDTO> getSeatsNotAvailableByZoneId(Long id);
    Set<ZoneDTO> getAllZonesByPlaceId(Long id);
}
