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
@Table(name = "Category")
public class Category {
    @Id
    @SequenceGenerator(name = "category-sequence", sequenceName = "category-sequence", allocationSize = 1)
    @GeneratedValue(generator = "category-sequence", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
}
