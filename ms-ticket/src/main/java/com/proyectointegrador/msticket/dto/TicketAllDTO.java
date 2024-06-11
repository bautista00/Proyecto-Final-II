package com.proyectointegrador.msticket.dto;

import com.proyectointegrador.msticket.domain.Event;
import com.proyectointegrador.msticket.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Schema(description = "Detalles de TicketAllDTO")
public class TicketAllDTO {
    @Schema(description = "Usuario correspondiente del ticket")
    private User user;

    @Schema(description = "ID del metodo de pago", example = "1")
    private Long paymentMethodId;

    @Schema(description = "Lista de ID's de los asientos")
    private List<Long> seatsId;

    @Schema(description = "Informacion del evento del ticket correspondiente")
    private Event event;
}
