package com.proyectointegrador.msplace.dto;

import com.proyectointegrador.msplace.domain.City;
import com.proyectointegrador.msplace.domain.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PlaceDTO {
    private Long id;
    private String name;
    private String neighborhood;
    private String street;
    private Integer number;
    private City city;
    private Set<ZoneOnlyDTO> zones;
    protected List<Event> events;
}
