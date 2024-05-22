package com.proyectointegrador.msevents.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class FechaEvento {
    private Long id;
    private Date fecha;
    private Long evento_id;
    private Long estadio_id;
}
