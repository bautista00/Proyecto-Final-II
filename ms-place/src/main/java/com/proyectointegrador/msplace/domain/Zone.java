package com.proyectointegrador.msplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Zone")
public class Zone {

    @Id
    @SequenceGenerator(name = "zone-sequence", sequenceName = "zone-sequence", allocationSize = 1)
    @GeneratedValue(generator = "zone-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Column(name = "quantity_seat")
    private Integer quantitySeat;
    private Integer availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany (mappedBy = "zone", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Seat> seats;
}
