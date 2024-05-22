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
@Table(name = "Estadio")
public class Estadio {
    @Id
    @SequenceGenerator(name = "estadio-sequence", sequenceName = "estadio-sequence", allocationSize = 1)
    @GeneratedValue(generator = "estadio-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String barrio;
    private String calle;
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;
}
