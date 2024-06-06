package com.proyectointegrador.msticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Detalles de Ticket Request")
public class TicketRequest {
    @Schema(description = "ID del usuario", example = "1")
    private String userId;

    @Schema(description = "ID del método de pago", example = "1")
    private Long paymentMethodId;
}
