package com.proyectointegrador.msusers.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class Ticket {
    private Long Id;
    private String userId;
    private Long paymentMethodId;
}
