package com.proyectointegrador.msticket.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Detalles de Place")
public class Place {
    @Schema(description = "ID del estadio", example = "1")
    private Long id;

    @Schema(description = "Nombre del estadio", example = "Estadio Monumental")
    private String name;

    @Schema(description = "Barrio donde se encuentra el estadio", example = "Belgrano")
    private String neighborhood;

    @Schema(description = "Calle del estadio", example = "Av. Pres. Figueroa Alcorta")
    private String street;

    @Schema(description = "Numero de la calle", example = "7597")
    private Integer number;
}
