package com.proyectointegrador.msticket.service.implement;

import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.repository.ITicketRepository;
import com.proyectointegrador.msticket.service.interfaces.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final ITicketRepository ticketRepository;

    @Override
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }



    @Override
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
