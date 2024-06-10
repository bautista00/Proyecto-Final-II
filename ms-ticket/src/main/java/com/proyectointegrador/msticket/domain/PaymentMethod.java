package com.proyectointegrador.msticket.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "paymentmethod")
@Schema(description = "Detalles de Payment Method")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del método de pago", example = "1")
    private Long Id;

    @Column(name = "category",nullable = false)
    @Schema(description = "Categoria del método de pago", example = "Credit Card")
    private String category;

    @Column(name="detail",nullable = false)
    @Schema(description = "Detalle del método de pago", example = "Visa")
    private String detail;

    public PaymentMethod(Long id) {
        this.Id = id;
    }
}
