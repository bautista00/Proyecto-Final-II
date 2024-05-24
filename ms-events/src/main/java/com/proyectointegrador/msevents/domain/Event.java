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
    @SequenceGenerator(name = "event-sequence", sequenceName = "event-sequence", allocationSize = 1)
    @GeneratedValue(generator = "event-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private String photo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "dateEvent_id")
    private DateEvent dateEvent;
}
