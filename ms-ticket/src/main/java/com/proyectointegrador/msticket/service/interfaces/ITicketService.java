package com.proyectointegrador.msticket.service.interfaces;

import com.proyectointegrador.msticket.domain.Ticket;

import java.util.List;
import java.util.Optional;

public interface ITicketService {
    Optional<Ticket> getTicketById(Long id);
    List<Ticket> getAllTickets();
    Ticket createTicket(Ticket ticket);
    void deleteTicket(Long id);
    List<Ticket> findByUserId(String id);
}
