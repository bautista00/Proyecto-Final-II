package com.proyectointegrador.msticket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Seat {
    private Long id;
    private Double price;
    private Long ticketId;
}

