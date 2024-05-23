package com.proyectointegrador.msticket.service.interfaces;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Ticket;

import java.util.List;
import java.util.Optional;

public interface IPaymentMethodService {

    Optional<PaymentMethod> getPaymentById(Long id);
    List<PaymentMethod> getAllPayments();
    PaymentMethod createPayment(PaymentMethod paymentMethod);
    PaymentMethod updatePayment(PaymentMethod paymentMethod);
    void deletePayment(PaymentMethod paymentMethod);
    Optional<PaymentMethod> getPaymentsByTicketId(Ticket ticket);
    List<PaymentMethod> getAllPaymentsByType(Ticket ticket);

}
