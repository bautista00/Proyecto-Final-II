package com.proyectointegrador.msticket.service.implement;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.repository.IPaymentMethodRepository;
import com.proyectointegrador.msticket.service.interfaces.IPaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements IPaymentMethodService {

    private final IPaymentMethodRepository paymentRepository;

    @Override
    public Optional<PaymentMethod> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<PaymentMethod> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentMethod createPayment(PaymentMethod paymentMethod) {
        return paymentRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod updatePayment(PaymentMethod paymentMethod) {
        return paymentRepository.save(paymentMethod);
    }

    @Override
    public void deletePayment(PaymentMethod paymentMethod) {
        paymentRepository.delete(paymentMethod);
    }

    @Override
    public Optional<PaymentMethod> getPaymentsByTicketId(Ticket ticket) {
        return paymentRepository.findById(ticket.getId());
    }

    @Override
    public List<PaymentMethod> getAllPaymentsByType(Ticket ticket) {
        return List.of();
    }
}
