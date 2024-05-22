package com.proyectointegrador.msplace.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplace.domain.Zone;
import com.proyectointegrador.msplace.dto.SeatDTO;
import com.proyectointegrador.msplace.dto.ZoneDTO;
import com.proyectointegrador.msplace.repository.IZoneRepository;
import com.proyectointegrador.msplace.service.interfaces.IZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ZoneService implements IZoneService {

    private final IZoneRepository zoneRepository;

    private final SeatService seatService;

    // averiguar si se sigue usando el mapper o usar stream
    private final ObjectMapper mapper;

    private ZoneDTO saveZone(ZoneDTO zoneDTO) {
        Zone zone = mapper.convertValue(zoneDTO, Zone.class);
        zoneRepository.save(zone);
        return mapper.convertValue(zone, ZoneDTO.class);
    }

    @Override
    public Optional<ZoneDTO> getZoneById(Long id) {
        Optional<Zone> zone = zoneRepository.findById(id);
        ZoneDTO zoneDTO = null;
        if (zone.isPresent()) {
            zoneDTO = mapper.convertValue(zone, ZoneDTO.class);
            return Optional.ofNullable(zoneDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ZoneDTO> getZoneByName(String name) {
        Optional<Zone> zone = zoneRepository.findByName(name);
        if (zone.isPresent()) {
            ZoneDTO zoneDTO = mapper.convertValue(zone, ZoneDTO.class);
            return Optional.ofNullable(zoneDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<ZoneDTO> getAllZones() {
        List<Zone> zones = zoneRepository.findAll();
        Set<ZoneDTO> zonesDTO = new HashSet<>();
        for (Zone zone : zones) {
            zonesDTO.add(mapper.convertValue(zone, ZoneDTO.class));
        }
        return zonesDTO;
    }

    @Override
    public ZoneDTO addZone(ZoneDTO zoneDTO) {
        return saveZone(zoneDTO);
    }

    @Override
    public ZoneDTO updateZone(ZoneDTO zoneDTO) {
        return saveZone(zoneDTO);
    }

    @Override
    public void deleteZoneById(Long id) {
        zoneRepository.deleteById(id);
    }

    @Override
    public void deleteZoneByName(String name) {
        Optional<Zone> zone = zoneRepository.findByName(name);
        zone.ifPresent(zoneRepository::delete);
    }

    @Override
    public Integer getAvailability(Long id) {
        Optional<Zone> zone = zoneRepository.findById(id);
        if (zone.isPresent()) {
            return zone.get().getAvailability();
        } else {
            return -1;
        }
    }

    // ver si al modificar el del asiento, se modifica aca tambien
    @Override
    public void putAvailability(Integer number, Long id) {
        // si recibe un 0 es porque tiene que restar un asiento
        // si recibe un 1 es porque tiene que sumar un asiento
        if (number == 0) {
            Optional<Zone> zone = zoneRepository.findById(id);
            zone.ifPresent(value -> value.setAvailability(value.getAvailability() - 1));
        } else if (number == 1) {
            Optional<Zone> zone = zoneRepository.findById(id);
            zone.ifPresent(value -> value.setAvailability(value.getAvailability() + 1));
        }
    }

    @Override
    public Set<SeatDTO> getSeatsByZoneId(Long id) {
        return seatService.getAllSeatsByZoneId(id);
    }

    @Override
    public Set<SeatDTO> getSeatsByZoneName(String name) {
        Optional<ZoneDTO> zone = getZoneByName(name);
        if (zone.isPresent()) {
            return seatService.getAllSeatsByZoneId(zone.get().getId());
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public Set<SeatDTO> getSeatsAvailableByZoneId(Long id) {
        Set<SeatDTO> seatDTOs = getSeatsByZoneId(id);
        Set<SeatDTO> availableSeatDTOs = new HashSet<>();
        for (SeatDTO seatDTO : seatDTOs) {
            if (seatDTO.getAvailability() == 1) {
                availableSeatDTOs.add(seatDTO);
            }
        }
        return availableSeatDTOs;
    }

    @Override
    public Set<SeatDTO> getSeatsNotAvailableByZoneId(Long id) {
        Set<SeatDTO> seatDTOs = getSeatsByZoneId(id);
        Set<SeatDTO> availableSeatDTOs = new HashSet<>();
        for (SeatDTO seatDTO : seatDTOs) {
            if (seatDTO.getAvailability() == 0) {
                availableSeatDTOs.add(seatDTO);
            }
        }
        return availableSeatDTOs;
    }

    @Override
    public Set<ZoneDTO> getAllZonesByPlaceId(Long id) {
        List<Zone> zones = zoneRepository.findAll();
        Set<ZoneDTO> zonesDTO = new HashSet<>();
        for (Zone zone : zones) {
            if (zone.getPlace().getId().equals(id)) {
                zonesDTO.add(mapper.convertValue(zone, ZoneDTO.class));
            }
        }
        return zonesDTO;
    }
}
