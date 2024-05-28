package com.proyectointegrador.msticket.controller;


import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.dto.TicketRequest;
import com.proyectointegrador.msticket.service.implement.TicketServiceImpl;
import com.proyectointegrador.msticket.service.interfaces.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final ITicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setUserId(String.valueOf(ticketRequest.getUserId()));
        ticket.setPaymentMethod(new PaymentMethod(ticketRequest.getPaymentMethodId()));
        Ticket ticketCreated = ticketService.createTicket(ticket);
        return ResponseEntity.ok(ticketCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketsById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}

// editar ticket?
// obtener ticket por id de usuario