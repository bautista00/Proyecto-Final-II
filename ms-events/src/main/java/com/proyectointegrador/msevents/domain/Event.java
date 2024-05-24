package com.proyectointegrador.msevents.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Event {
    private Long id;
    private String name;
    private String description;
    private String photo;
    private Category category;
    private EventDate eventDate;
}
