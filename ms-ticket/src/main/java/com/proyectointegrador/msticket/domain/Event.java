package com.proyectointegrador.msticket.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Detalles de Event")
public class Event {
    @Schema(description = "ID del evento", example = "1")
    private Long id;

    @Schema(description = "Nombre del evento", example = "After Hours til Dawn Tour")
    private String name;

    @Schema(description = "Descripcion del evento", example = "After Hours til Dawn Tour en River, The Weeknd presenta sus dos albumes en el Estadio Monumental")
    private String description;

    @Schema(description = "Link de la foto del evento", example = "theweeknd.jpg")
    private String photo;

    @Schema(description = "Fecha del evento", example = "2024-09-14T22:15:00Z")
    private DateEvent dateEvent;

    @Schema(description = "Estadio donde se hace el evento", example = "Estadio Monumental")
    private Place place;
}
