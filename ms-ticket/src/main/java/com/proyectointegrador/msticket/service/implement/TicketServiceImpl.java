package com.proyectointegrador.msticket.service.implement;

import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Seat;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.exception.PaymentMethodNotFoundException;
import com.proyectointegrador.msticket.exception.TicketNotFoundException;
import com.proyectointegrador.msticket.repository.IPaymentMethodRepository;
import com.proyectointegrador.msticket.repository.ITicketRepository;
import com.proyectointegrador.msticket.repository.SeatRepository;
import com.proyectointegrador.msticket.service.interfaces.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements ITicketService {

    private final ITicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final IPaymentMethodRepository iPaymentMethodRepository;

    private void findSeatsByTicket(Ticket ticket) {
        List<Seat> seats = seatRepository.findByTicketId(ticket.getId());
        ticket.setSeats(seats);
    }

    @Override
    public Optional<Ticket> getTicketById(Long id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        optionalTicket.ifPresent(this::findSeatsByTicket);
        return optionalTicket;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        tickets.forEach(this::findSeatsByTicket);
        return tickets;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        PaymentMethod paymentMethod =
                iPaymentMethodRepository.findById(ticket.getPaymentMethod().getId())
                .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method not found"));
        ticket.setPaymentMethod(paymentMethod);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        if(!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException("Ticket not found");
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public List<Ticket> findByUserId(String id) {
        return ticketRepository.findByUserId(id);
    }
}
