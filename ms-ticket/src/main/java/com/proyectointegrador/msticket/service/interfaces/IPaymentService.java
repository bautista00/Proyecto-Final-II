package com.proyectointegrador.msticket.service.interfaces;

import com.proyectointegrador.msticket.domain.Payment;
import com.proyectointegrador.msticket.domain.Ticket;

import java.util.List;
import java.util.Optional;

public interface IPaymentService {

    Optional<Payment> getPaymentById(int id);
    List<Payment> getAllPayments();
    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    void deletePayment(Payment payment);
    Optional<Payment> getPaymentsByTicketId(Ticket ticket);
    List<Payment> getAllPaymentsByType(Ticket ticket);

}
