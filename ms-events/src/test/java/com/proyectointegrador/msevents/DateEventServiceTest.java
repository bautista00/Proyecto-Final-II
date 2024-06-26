package com.proyectointegrador.msevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.DateEvent;
import com.proyectointegrador.msevents.dto.DateEventDTO;
import com.proyectointegrador.msevents.repository.IDateEventRepository;
import com.proyectointegrador.msevents.service.implement.DateEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DateEventServiceTest {

    @Mock
    private IDateEventRepository dateEventRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private DateEventService dateEventService;

    private DateEvent dateEvent;
    private DateEventDTO dateEventDTO;
    private Date date;

    @BeforeEach
    void setUp() {
        dateEvent = new DateEvent();
        dateEvent.setId(1L);
        dateEvent.setDate(new Date());

        dateEventDTO = new DateEventDTO();
        dateEventDTO.setId(1L);
        dateEventDTO.setDate(new Date());

        date = new Date();
    }

    @Test
    void getDateEventById_shouldReturnDateEventDTO() {
        when(dateEventRepository.findDateEventById(1L)).thenReturn(Optional.of(dateEvent));
        when(mapper.convertValue(eq(Optional.of(dateEvent)), eq(DateEventDTO.class))).thenReturn(dateEventDTO);
        Optional<DateEventDTO> result = dateEventService.getDateEventById(1L);
        assertTrue(result.isPresent());
        assertEquals(dateEventDTO, result.get());
        verify(dateEventRepository).findDateEventById(1L);

        verify(mapper).convertValue(eq(Optional.of(dateEvent)), eq(DateEventDTO.class));
    }



    @Test
    void getDateEventByDate_shouldReturnDateEventDTO() {
        when(dateEventRepository.findDateEventByDate(date)).thenReturn(Optional.of(dateEvent));
        when(mapper.convertValue(eq(Optional.of(dateEvent)), eq(DateEventDTO.class))).thenReturn(dateEventDTO);
        Optional<DateEventDTO> result = dateEventService.getDateEventByDate(date);
        assertTrue(result.isPresent());
        assertEquals(dateEventDTO, result.get());
        verify(dateEventRepository).findDateEventByDate(date);
        verify(mapper).convertValue(eq(Optional.of(dateEvent)), eq(DateEventDTO.class));
    }


    @Test
    void getAllDateEvents_shouldReturnSetOfDateEventDTOs() {
        List<DateEvent> dateEvents = Collections.singletonList(dateEvent);
        when(dateEventRepository.findAll()).thenReturn(dateEvents);
        when(mapper.convertValue(dateEvent, DateEventDTO.class)).thenReturn(dateEventDTO);

        Set<DateEventDTO> result = dateEventService.getAllDateEvents();

        assertEquals(1, result.size());
        assertTrue(result.contains(dateEventDTO));
        verify(dateEventRepository).findAll();
        verify(mapper).convertValue(dateEvent, DateEventDTO.class);
    }

    @Test
    void addDateEvent_shouldReturnSavedDateEventDTO() {
        when(mapper.convertValue(dateEventDTO, DateEvent.class)).thenReturn(dateEvent);
        when(dateEventRepository.save(dateEvent)).thenReturn(dateEvent);
        when(mapper.convertValue(dateEvent, DateEventDTO.class)).thenReturn(dateEventDTO);

        DateEventDTO result = dateEventService.addDateEvent(dateEventDTO);

        assertEquals(dateEventDTO, result);
        verify(dateEventRepository).save(dateEvent);
        verify(mapper).convertValue(dateEventDTO, DateEvent.class);
        verify(mapper).convertValue(dateEvent, DateEventDTO.class);
    }

    @Test
    void updateDateEvent_shouldReturnUpdatedDateEventDTO() {
        when(mapper.convertValue(dateEventDTO, DateEvent.class)).thenReturn(dateEvent);
        when(dateEventRepository.save(dateEvent)).thenReturn(dateEvent);
        when(mapper.convertValue(dateEvent, DateEventDTO.class)).thenReturn(dateEventDTO);

        DateEventDTO result = dateEventService.updateDateEvent(dateEventDTO);

        assertEquals(dateEventDTO, result);
        verify(dateEventRepository).save(dateEvent);
        verify(mapper).convertValue(dateEventDTO, DateEvent.class);
        verify(mapper).convertValue(dateEvent, DateEventDTO.class);
    }

    @Test
    void deleteDateEventById_shouldInvokeRepositoryDeleteById() {
        dateEventService.deleteDateEventById(1L);

        verify(dateEventRepository).deleteById(1L);
    }

    @Test
    void deleteDateEventByDate_shouldInvokeRepositoryDelete() {
        when(dateEventRepository.findDateEventByDate(date)).thenReturn(Optional.of(dateEvent));

        dateEventService.deleteDateEventByDate(date);

        verify(dateEventRepository).delete(dateEvent);
    }
}
