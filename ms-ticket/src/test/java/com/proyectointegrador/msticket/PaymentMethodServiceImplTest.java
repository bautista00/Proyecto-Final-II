package com.proyectointegrador.msticket;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.exception.PaymentMethodNotFoundException;
import com.proyectointegrador.msticket.repository.IPaymentMethodRepository;
import com.proyectointegrador.msticket.repository.ITicketRepository;
import com.proyectointegrador.msticket.service.implement.PaymentMethodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PaymentMethodServiceImplTest {

    @Mock
    private IPaymentMethodRepository paymentMethodRepository;

    @Mock
    private ITicketRepository ticketRepository;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPaymentById_Success() {
        Long paymentId = 1L;
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(paymentId);
        when(paymentMethodRepository.findById(paymentId)).thenReturn(Optional.of(paymentMethod));

        Optional<PaymentMethod> result = paymentMethodService.getPaymentById(paymentId);

        assertTrue(result.isPresent());
        assertEquals(paymentId, result.get().getId());
    }

    @Test
    void getPaymentById_NotFound() {
        Long paymentId = 1L;
        when(paymentMethodRepository.findById(paymentId)).thenReturn(Optional.empty());

        Optional<PaymentMethod> result = paymentMethodService.getPaymentById(paymentId);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllPayments_Success() {
        PaymentMethod paymentMethod1 = new PaymentMethod();
        PaymentMethod paymentMethod2 = new PaymentMethod();
        when(paymentMethodRepository.findAll()).thenReturn(Arrays.asList(paymentMethod1, paymentMethod2));

        List<PaymentMethod> result = paymentMethodService.getAllPayments();

        assertEquals(2, result.size());
    }

    @Test
    void createPayment_Success() {
        PaymentMethod paymentMethod = new PaymentMethod();
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);

        PaymentMethod result = paymentMethodService.createPayment(paymentMethod);

        assertNotNull(result);
    }

    @Test
    void updatePayment_Success() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        when(paymentMethodRepository.existsById(paymentMethod.getId())).thenReturn(true);
        when(paymentMethodRepository.save(any(PaymentMethod.class))).thenReturn(paymentMethod);

        PaymentMethod result = paymentMethodService.updatePayment(paymentMethod);

        assertNotNull(result);
    }

    @Test
    void updatePayment_NotFound() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        when(paymentMethodRepository.existsById(paymentMethod.getId())).thenReturn(false);

        assertThrows(PaymentMethodNotFoundException.class, () -> {
            paymentMethodService.updatePayment(paymentMethod);
        });
    }

    @Test
    void deletePayment_Success() {
        Long paymentId = 1L;
        when(paymentMethodRepository.existsById(paymentId)).thenReturn(true);

        paymentMethodService.deletePayment(paymentId);

        verify(paymentMethodRepository, times(1)).deleteById(paymentId);
    }

    @Test
    void deletePayment_NotFound() {
        Long paymentId = 1L;
        when(paymentMethodRepository.existsById(paymentId)).thenReturn(false);

        assertThrows(PaymentMethodNotFoundException.class, () -> {
            paymentMethodService.deletePayment(paymentId);
        });
    }

    @Test
    void getPaymentsByTicketId_Success() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        PaymentMethod paymentMethod = new PaymentMethod();
        ticket.setPaymentMethod(paymentMethod);
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        Optional<PaymentMethod> result = paymentMethodService.getPaymentsByTicketId(ticketId);

        assertTrue(result.isPresent());
        assertEquals(paymentMethod, result.get());
    }

    @Test
    void getPaymentsByTicketId_NotFound() {
        Long ticketId = 1L;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        Optional<PaymentMethod> result = paymentMethodService.getPaymentsByTicketId(ticketId);

        assertFalse(result.isPresent());
    }

    @Test
    void getAllPaymentsByCategory_Success() {
        String category = "Credit Card";
        PaymentMethod paymentMethod1 = new PaymentMethod();
        PaymentMethod paymentMethod2 = new PaymentMethod();
        when(paymentMethodRepository.findByCategory(category)).thenReturn(Arrays.asList(paymentMethod1, paymentMethod2));

        List<PaymentMethod> result = paymentMethodService.getAllPaymentsByCategory(category);

        assertEquals(2, result.size());
    }
}
