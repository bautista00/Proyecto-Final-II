package com.proyectointegrador.msplace.dto;

import com.proyectointegrador.msplace.domain.Place;
import com.proyectointegrador.msplace.domain.Seat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ZoneOnlyDTO {
    private Long id;
    private String name;
    private Integer quantitySeat;
    private Integer availability;
    private Set<SeatOnlyDTO> seats;
}