package com.proyectointegrador.msestadio.domain;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Ciudad {
    private Long id;
    private String nombre;
    private Integer codigo_postal;
}
