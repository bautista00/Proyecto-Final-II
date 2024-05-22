package com.proyectointegrador.msplace.dto;

import com.proyectointegrador.msplace.domain.Place;
import com.proyectointegrador.msplace.domain.Seat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class ZoneDTO {
    private Long id;
    private String name;
    private Integer numberSeats;
    private Integer availability;
    private Place place;
    private Set<Seat> seats = new HashSet<>();
}
