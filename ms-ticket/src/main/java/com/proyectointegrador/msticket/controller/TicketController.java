package com.proyectointegrador.msticket.controller;


import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.dto.TicketRequest;
import com.proyectointegrador.msticket.service.implement.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private TicketServiceImpl ticketService;


    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest ticketRequest) {
//        String userId=
        Ticket ticket = new Ticket();
//        ticket.setUserId();
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

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
