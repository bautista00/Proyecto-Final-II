package com.proyectointegrador.msticket.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DateEvent {
    private Long id;
    private Date date;
}
