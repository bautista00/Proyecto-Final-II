package com.proyectointegrador.msplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Zone")
@Schema(name = "Detalles de Zone")
public class Zone {

    @Id
    @SequenceGenerator(name = "zone-sequence", sequenceName = "zone-sequence", allocationSize = 1)
    @GeneratedValue(generator = "zone-sequence", strategy = GenerationType.SEQUENCE)
    @Schema(description = "ID de la zona", example = "1")
    private Long id;

    @Schema(description = "Nombre de la zona", example = "Popular Norte")
    private String name;

    @Column(name = "quantity_seat")
    @Schema(description = "Cantidad de asientos en la zona", example = "150")
    private Integer quantitySeat;

    @Schema(description = "Cantidad de asientos disponibles en la zona", example = "150")
    private Integer availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    @Schema(description = "ID del estadio", example = "1")
    private Place place;

    @OneToMany (mappedBy = "zone", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Asientos")
    private Set<Seat> seats;
}
