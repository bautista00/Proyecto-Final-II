package com.proyectointegrador.msticket.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@AllArgsConstructor
@Data
@Schema(description = "Detalles de DateEvent")
public class DateEvent {
    @Schema(description = "ID de la fecha del evento", example = "1")
    private Long id;

    @Schema(description = "Fecha del evento", example = "2024-09-14T22:15:00Z")
    private Date date;
}
