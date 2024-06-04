package com.proyectointegrador.msticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketCreateDTO {
    private String userId;
    private Long paymentMethodId;
    private List<Long> seatsId;
    private Long eventId;
}
