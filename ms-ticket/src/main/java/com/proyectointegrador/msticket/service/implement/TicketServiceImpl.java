package com.proyectointegrador.msticket.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Seat;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.domain.User;
import com.proyectointegrador.msticket.dto.TicketAllDTO;
import com.proyectointegrador.msticket.dto.TicketCreateDTO;
import com.proyectointegrador.msticket.dto.EmailDTO;
import com.proyectointegrador.msticket.exception.PaymentMethodNotFoundException;
import com.proyectointegrador.msticket.exception.TicketNotFoundException;
import com.proyectointegrador.msticket.repository.IPaymentMethodRepository;
import com.proyectointegrador.msticket.repository.ITicketRepository;
import com.proyectointegrador.msticket.repository.SeatRepository;
import com.proyectointegrador.msticket.repository.UserRepository;
import com.proyectointegrador.msticket.service.interfaces.IEmailService;
import com.proyectointegrador.msticket.service.interfaces.ITicketService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements ITicketService {

    private final ITicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final IPaymentMethodRepository iPaymentMethodRepository;
    private final ObjectMapper mapper;
    private final IEmailService emailService;
    private final UserRepository userRepository;

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
    public List<TicketAllDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        tickets.forEach(this::findSeatsByTicket);
        return tickets.stream()
                .map(ticket -> {
                    TicketAllDTO ticketDTO = mapper.convertValue(ticket, TicketAllDTO.class);
                    Optional<User> user = userRepository.findUserById(ticket.getUserId());
                    user.ifPresent(ticketDTO::setUser);
                    ticketDTO.setSeatsId(ticket.getSeats().stream().map(Seat::getId).collect(Collectors.toList()));
                    ticketDTO.setPaymentMethodId(ticket.getPaymentMethod().getId());
                    return ticketDTO;
                })
                .collect(Collectors.toList());
    }

    private String buildEmailMessage(User user, Ticket ticket, List<Seat> seats) {
        StringBuilder message = new StringBuilder();
        message.append("<h3><strong>¡Muchas gracias por tu compra, ").append(user.getFirstName()).append("!</strong></h3><br>")
                .append("<strong>Detalles de su compra:</strong><br><br>")
                .append("<strong>Número de compra:</strong> ").append(ticket.getId()).append("<br>")
                .append("<strong>Método de Pago:</strong> ").append(ticket.getPaymentMethod().getCategory()).append("<br>")
                .append("<strong>Tarjeta:</strong> ").append(ticket.getPaymentMethod().getDetail()).append("<br><br>")
                .append("<strong>Asientos:</strong><br>");
        for (Seat seat : seats) {
            message.append("- Número de asiento: ").append(seat.getId()).append("<br>");
        }
        message.append("<br>¡Que lo disfrutes!");
        return message.toString();
    }

    @Override
    public Ticket createTicket(TicketCreateDTO ticketDTO) throws MessagingException {
        Ticket ticket = mapper.convertValue(ticketDTO, Ticket.class);
        PaymentMethod paymentMethod =
                iPaymentMethodRepository.findById(ticketDTO.getPaymentMethodId())
                .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method not found"));
        ticket.setPaymentMethod(paymentMethod);
        List<Seat> seats = new ArrayList<>();
        for (Long seatId : ticketDTO.getSeatsId()) {
            Seat seat = seatRepository.findSeatById(seatId);
            seat.setTicketId(ticket.getId());
            seats.add(seat);
        }
        ticket.setSeats(seats);
        ticket = ticketRepository.save(ticket);
        for (Seat seat : seats) {
            seat.setTicketId(ticket.getId());
            seatRepository.updateSeatByTicket(seat);
        }
        Optional<User> user = userRepository.findUserById(ticket.getUserId());
        if (user.isPresent()) {
            String receiver = user.get().getEmail();
            String subject = "Confirmación de compra";
            String message = buildEmailMessage(user.get(), ticket, seats);
            EmailDTO emailDTO = new EmailDTO(receiver, subject, message);
            emailService.sendMail(emailDTO);
        }
        return ticket;
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
