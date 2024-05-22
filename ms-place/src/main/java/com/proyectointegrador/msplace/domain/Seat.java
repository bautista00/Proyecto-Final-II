package com.proyectointegrador.msplace.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Seat")
public class Seat {

    @Id
    @SequenceGenerator(name = "seat-sequence", sequenceName = "seat-sequence", allocationSize = 1)
    @GeneratedValue(generator = "seat-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer availability;
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zone_id")
    private Zone zone;
}

// falta el id de la entrada acá
// estamos haciendo relaciones bidireccionales
// una entrada puede tener muchos asientos One to many
// un asiento pertenece a una sola entrada Many to One