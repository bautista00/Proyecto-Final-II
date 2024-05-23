package com.proyectointegrador.msticket.repository;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
