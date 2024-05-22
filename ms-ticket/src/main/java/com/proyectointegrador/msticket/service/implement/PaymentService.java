package com.proyectointegrador.msticket.service.implement;

import com.proyectointegrador.msticket.domain.Payment;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.repository.IPaymentRepository;
import com.proyectointegrador.msticket.service.interfaces.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    @Override
    public Optional<Payment> getPaymentById(int id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Payment payment) {
        paymentRepository.delete(payment);
    }

    @Override
    public Optional<Payment> getPaymentsByTicketId(Ticket ticket) {
        return paymentRepository.findById(ticket.getId());
    }

    @Override
    public List<Payment> getAllPaymentsByType(Ticket ticket) {
        return List.of();
    }
}
