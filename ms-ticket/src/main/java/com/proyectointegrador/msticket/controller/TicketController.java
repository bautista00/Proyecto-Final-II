package com.proyectointegrador.msticket.controller;


import com.proyectointegrador.msticket.domain.PaymentMethod;
import com.proyectointegrador.msticket.domain.Ticket;
import com.proyectointegrador.msticket.dto.TicketCreateDTO;
import com.proyectointegrador.msticket.dto.TicketRequest;
import com.proyectointegrador.msticket.service.implement.TicketServiceImpl;
import com.proyectointegrador.msticket.service.interfaces.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Tag(name = "Ticket Controller", description = "Operaciones relacionadas a Tickets")
public class TicketController {

    private final ITicketService ticketService;

    @Operation(summary = "Crear un ticket", description = "Crea un nuevo ticket",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                value = """
                                        {
                                            "userId": "f7049e0e-0a21-4e19-819d-bf8915f2998f",
                                            "paymentMethodId": 2,
                                            "seatsId": [13, 16]
                                        }
                                        """
                        )
                )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket creado"),
    })
    @PostMapping("/create")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketCreateDTO ticketRequest) {
        Ticket ticketCreated = ticketService.createTicket(ticketRequest);
        return ResponseEntity.ok(ticketCreated);
    }

    @Operation(summary = "Obtener ticket por Id", description = "Devuelve un ticket basado en Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketsById(@Parameter(description = "ID del ticket a obtener", example = "1") @PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.getTicketById(id);
        return ticket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener todos los tickets", description = "Devuelve una lista de todos los tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Obtener ticket por Id del usuario", description = "Devuelve un ticket basado en el Id del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<List<Ticket>> findByUserId(@Parameter(description = "ID del usuario para obtener el ticket", example = "f7049e0e-0a21-4e19-819d-bf8915f2998f") @PathVariable String id) {
        return ResponseEntity.ok().body(ticketService.findByUserId(id));
    }

    @Operation(summary = "Eliminar ticket", description = "Elimina el ticket basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "203", description = "Ticket eliminado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTicket(@Parameter(description = "ID del ticket a eliminar", example = "1") @PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}