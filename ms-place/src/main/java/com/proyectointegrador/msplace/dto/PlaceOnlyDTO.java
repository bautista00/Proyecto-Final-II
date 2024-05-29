package com.proyectointegrador.msplace.dto;

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
public class PlaceOnlyDTO {
    private Long id;
    private String name;
    private String neighborhood;
    private String street;
    private Integer number;
    private Set<ZoneOnlyDTO> zones;
    protected List<Event> events;
}
