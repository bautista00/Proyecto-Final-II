package com.proyectointegrador.msevents.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class City {
    private Long id;
    private String name;
    private String zipCode;
}
