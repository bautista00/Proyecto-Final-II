package com.proyectointegrador.msevents.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class EventDate {
    private Long id;
    private Date date;
}
