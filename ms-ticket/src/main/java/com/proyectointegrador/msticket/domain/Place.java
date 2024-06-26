package com.proyectointegrador.msticket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Place {
    private Long id;
    private String name;
    private String neighborhood;
    private String street;
    private Integer number;
}
