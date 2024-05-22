package com.proyectointegrador.msevents.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Evento {
    private Long id;
    private String nombre;
    private String descripcion;
    private String foto;
}
