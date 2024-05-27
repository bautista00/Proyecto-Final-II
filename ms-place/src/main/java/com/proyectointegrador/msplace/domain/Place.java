package com.proyectointegrador.msplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Place")
public class Place {

    @Id
    @SequenceGenerator(name = "place-sequence", sequenceName = "place-sequence", allocationSize = 1)
    @GeneratedValue(generator = "place-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String neighborhood;
    private String street;
    private Integer number;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany (mappedBy = "place", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Zone> zones;
}
