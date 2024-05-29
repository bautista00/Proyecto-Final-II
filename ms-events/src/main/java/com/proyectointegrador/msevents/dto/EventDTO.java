package com.proyectointegrador.msevents.dto;
import com.proyectointegrador.msevents.domain.Category;
import com.proyectointegrador.msevents.domain.DateEvent;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private String photo;
    private Category category;
    private DateEvent dateEvent;
    private Long placeId;
}
