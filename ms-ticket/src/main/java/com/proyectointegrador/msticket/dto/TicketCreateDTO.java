package com.proyectointegrador.msticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Detalles de TicketCreateDTO")
public class TicketCreateDTO {
    @Schema(description = "ID del usuario", example = "1")
    private String userId;

    @Schema(description = "ID del método de pago", example = "1")
    private Long paymentMethodId;

    @Schema(description = "Lista con ID de los asientos")
    private List<Long> seatsId;
}
