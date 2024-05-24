package com.proyectointegrador.msevents.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EventDateDTO {
    private Long id;
    private Date date;
}
