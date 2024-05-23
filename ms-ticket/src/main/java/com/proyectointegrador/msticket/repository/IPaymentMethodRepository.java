package com.proyectointegrador.msticket.repository;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByCategory(String category);
}
