package com.proyectointegrador.msestadio.domain;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Asiento {
    private Long id;
    private Integer disponibilidad;
    private Double precio;
    private Long zona_id;
}
