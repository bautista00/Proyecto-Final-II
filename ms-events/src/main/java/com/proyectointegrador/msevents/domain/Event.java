package com.proyectointegrador.msevents.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "images_id", referencedColumnName = "id")
    private Images images;

    @Column(name ="place_id",nullable = false)
    private Long placeId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "dateEvent_id")
    private DateEvent dateEvent;
}
