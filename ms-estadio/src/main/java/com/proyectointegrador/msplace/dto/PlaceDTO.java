package com.proyectointegrador.msplace.dto;

import com.proyectointegrador.msplace.domain.City;
import com.proyectointegrador.msplace.domain.Zone;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class PlaceDTO {
    private Long id;
    private String name;
    private String neighborhood;
    private String street;
    private Integer number;
    private City city;
    private Set<Zone> zones = new HashSet<>();
}
