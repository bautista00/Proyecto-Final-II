package com.proyectointegrador.msticket.domain;


import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User userId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment paymentId;

}
