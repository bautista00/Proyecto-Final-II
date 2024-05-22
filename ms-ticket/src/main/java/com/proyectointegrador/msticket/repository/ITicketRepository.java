package com.proyectointegrador.msticket.repository;

import com.proyectointegrador.msticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket, Integer> {
}
