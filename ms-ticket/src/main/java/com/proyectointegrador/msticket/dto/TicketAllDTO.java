package com.proyectointegrador.msticket.dto;

import com.proyectointegrador.msticket.domain.Event;
import com.proyectointegrador.msticket.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TicketAllDTO {
    private User user;
    private Long paymentMethodId;
    private List<Long> seatsId;
    private Event event;
}
