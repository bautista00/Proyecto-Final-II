package com.proyectointegrador.msplace.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SeatOnlyDTO {
    private Long id;
    private Integer availability;
    private Double price;
    private Long ticketId;
}
