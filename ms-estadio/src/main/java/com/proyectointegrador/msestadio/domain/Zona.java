package com.proyectointegrador.msestadio.domain;
import jakarta.persistence.*;
import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString

@Entity
@Table
public class Zona {
    @Id
    @SequenceGenerator(name = "zona-sequence", sequenceName = "zona-sequence", allocationSize = 1)
    @GeneratedValue(generator = "zona-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private Integer cantidad_asientos;
    private Integer disponibilidad;

    @OneToMany
    @JoinColumn(name = "estadio_id")
    private Estadio estadio;
}
