package com.proyectointegrador.msticket.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(description = "Detalles de Seat")
public class Seat {
    @Schema(description = "ID del asiento", example = "1")
    private Long id;

    @Schema(description = "Precio del asiento", example = "100.0")
    private Double price;

    @Schema(description = "ID del ticket", example = "1")
    private Long ticketId;
}

