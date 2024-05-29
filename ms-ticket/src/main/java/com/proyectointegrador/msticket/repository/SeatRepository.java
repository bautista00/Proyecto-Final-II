package com.proyectointegrador.msticket.repository;

import com.proyectointegrador.msticket.domain.Seat;
import com.proyectointegrador.msticket.repository.feign.FeignSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepository {

    private final FeignSeatRepository seatRepository;

    public List<Seat> findByTicketId(Long id) {
        return seatRepository.findByTicketId(id);
    }
}
