package com.proyectointegrador.msevents.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "DateEvent")
public class DateEvent {
    @Id
    @SequenceGenerator(name = "dateEvent-sequence", sequenceName = "dateEvent-sequence", allocationSize = 1)
    @GeneratedValue(generator = "dateEvent-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private Date date;
}
