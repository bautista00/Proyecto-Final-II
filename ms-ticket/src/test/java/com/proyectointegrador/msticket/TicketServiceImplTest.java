package com.proyectointegrador.msticket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msticket.domain.*;
import com.proyectointegrador.msticket.dto.EmailDTO;
import com.proyectointegrador.msticket.dto.TicketAllDTO;
import com.proyectointegrador.msticket.dto.TicketCreateDTO;
import com.proyectointegrador.msticket.exception.PaymentMethodNotFoundException;
import com.proyectointegrador.msticket.exception.TicketNotFoundException;
import com.proyectointegrador.msticket.repository.*;
import com.proyectointegrador.msticket.service.implement.TicketServiceImpl;
import com.proyectointegrador.msticket.service.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


public class TicketServiceImplTest {

    @Mock
    private ITicketRepository ticketRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private IPaymentMethodRepository paymentMethodRepository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private IEmailService emailService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTicketById_Success() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId("user123");
        ticket.setEventId(1L);
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        ticket.setPaymentMethod(paymentMethod);
        List<Seat> seats = Arrays.asList(new Seat(1L, 1.0, 123456789L), new Seat(2L, 1.0, 12345L));
        ticket.setSeats(seats);

        User user = new User();
        user.setId("user123");
        user.setEmail("user@example.com");

        Event event = new Event();
        event.setId(1L);
        event.setName("Event");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(userRepository.findUserById("user123")).thenReturn(Optional.of(user));
        when(eventRepository.findEventById(1L)).thenReturn(event);
        when(seatRepository.findByTicketId(1L)).thenReturn(seats);

        TicketAllDTO ticketAllDTO = new TicketAllDTO();
        ticketAllDTO.setUser(user);
        ticketAllDTO.setEvent(event);
        ticketAllDTO.setSeatsId(Arrays.asList(1L, 2L));
        ticketAllDTO.setPaymentMethodId(1L);

        when(mapper.convertValue(ticket, TicketAllDTO.class)).thenReturn(ticketAllDTO);

        Optional<TicketAllDTO> result = ticketService.getTicketById(1L);

        assertTrue(result.isPresent());
        assertEquals(ticketAllDTO, result.get());
    }

    @Test
    void getTicketById_NotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TicketAllDTO> result = ticketService.getTicketById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void createTicket_Success() throws MessagingException {
        TicketCreateDTO ticketCreateDTO = new TicketCreateDTO();
        ticketCreateDTO.setUserId("user123");
        ticketCreateDTO.setEventId(1L);
        ticketCreateDTO.setPaymentMethodId(1L);
        ticketCreateDTO.setSeatsId(Arrays.asList(1L, 2L));

        ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapper.convertValue(Mockito.any(), Mockito.eq(Ticket.class))).thenReturn(new Ticket());

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        paymentMethod.setCategory("algo");
        paymentMethod.setDetail("algo");
        when(paymentMethodRepository.findById(1L)).thenReturn(Optional.of(paymentMethod));

        Seat seat1 = new Seat(1L, 1.0, 44444L);
        Seat seat2 = new Seat(2L, 1.0, 55555L);
        when(seatRepository.findSeatById(1L)).thenReturn(seat1);
        when(seatRepository.findSeatById(2L)).thenReturn(seat2);

        DateEvent dateEvent = new DateEvent();
        dateEvent.setId(1L);
        Date date = new Date();
        date.setTime(12);
        dateEvent.setDate(date);

        Place place = new Place();
        place.setName("hola");

        Event event = new Event();
        event.setId(1L);
        event.setName("Event");
        event.setDateEvent(dateEvent);
        event.setPlace(place);
        when(eventRepository.findEventById(1L)).thenReturn(event);


        User user = new User();
        user.setId("user123");
        user.setEmail("user@example.com");
        when(userRepository.findUserById("user123")).thenReturn(Optional.of(user));

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setUserId("user123");
        savedTicket.setEventId(1L);
        savedTicket.setEvent(event);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        when(mapper.convertValue(ticketCreateDTO,Ticket.class)).thenReturn(savedTicket);
        Ticket result = ticketService.createTicket(ticketCreateDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user123", result.getUserId());
        assertEquals(1L, result.getEventId());
        assertEquals(paymentMethod, result.getPaymentMethod());
        assertEquals(2, result.getSeats().size());
        verify(emailService, times(1)).sendMail(any(EmailDTO.class));
    }



    @Test
    void createTicket_PaymentMethodNotFound() {
        TicketCreateDTO ticketCreateDTO = new TicketCreateDTO();
        ticketCreateDTO.setPaymentMethodId(1L);

        when(paymentMethodRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PaymentMethodNotFoundException.class, () -> ticketService.createTicket(ticketCreateDTO));
    }

    @Test
    void deleteTicket_Success() {
        when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.deleteTicket(1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTicket_NotFound() {
        when(ticketRepository.existsById(1L)).thenReturn(false);

        assertThrows(TicketNotFoundException.class, () -> ticketService.deleteTicket(1L));
    }

    @Test
    void getAllTickets_Success() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setUserId("user123");
        ticket.setEventId(1L);
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        ticket.setPaymentMethod(paymentMethod);
        List<Seat> seats = Arrays.asList(new Seat(1L, 1.0, 99999L), new Seat(2L, 1.0, 2222222L));
        ticket.setSeats(seats);

        User user = new User();
        user.setId("user123");
        user.setEmail("user@example.com");

        Event event = new Event();
        event.setId(1L);
        event.setName("Event");

        List<Ticket> tickets = Arrays.asList(ticket);

        when(ticketRepository.findAll()).thenReturn(tickets);
        when(userRepository.findUserById("user123")).thenReturn(Optional.of(user));
        when(eventRepository.findEventById(1L)).thenReturn(event);
        when(seatRepository.findByTicketId(1L)).thenReturn(seats);

        TicketAllDTO ticketAllDTO = new TicketAllDTO();
        ticketAllDTO.setUser(user);
        ticketAllDTO.setEvent(event);
        ticketAllDTO.setSeatsId(Arrays.asList(1L, 2L));
        ticketAllDTO.setPaymentMethodId(1L);

        when(mapper.convertValue(ticket, TicketAllDTO.class)).thenReturn(ticketAllDTO);

        List<TicketAllDTO> result = ticketService.getAllTickets();

        assertEquals(1, result.size());
        assertEquals(ticketAllDTO, result.get(0));
    }
}
