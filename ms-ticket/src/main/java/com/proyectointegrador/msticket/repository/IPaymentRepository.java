package com.proyectointegrador.msticket.repository;

import com.proyectointegrador.msticket.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
}
