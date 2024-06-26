package com.proyectointegrador.msevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msevents.domain.DateEvent;
import com.proyectointegrador.msevents.domain.Event;
import com.proyectointegrador.msevents.domain.Place;
import com.proyectointegrador.msevents.dto.CategoryDTO;
import com.proyectointegrador.msevents.dto.EventDTO;
import com.proyectointegrador.msevents.dto.EventGetDTO;
import com.proyectointegrador.msevents.exceptions.ResourceNotFoundException;
import com.proyectointegrador.msevents.repository.IDateEventRepository;
import com.proyectointegrador.msevents.repository.IEventRepository;
import com.proyectointegrador.msevents.repository.PlaceRepository;
import com.proyectointegrador.msevents.service.implement.EventService;
import com.proyectointegrador.msevents.service.interfaces.ICategoryService;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IDateEventRepository dateEventRepository;

    @Mock
    private ICategoryService categoryService;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private EventDTO eventDTO;
    private EventGetDTO eventGetDTO;
    private DateEvent dateEvent;
    private Place place;

    @BeforeEach
    void setUp() {
        dateEvent = new DateEvent();
        dateEvent.setId(1L);

        place = new Place();
        place.setId(1L);

        event = new Event();
        event.setId(1L);
        event.setDateEvent(dateEvent);
        event.setPlaceId(1L);

        eventDTO = new EventDTO();
        eventDTO.setDateEvent(dateEvent);

        eventGetDTO = new EventGetDTO();
        eventGetDTO.setId(1L);
        eventGetDTO.setDateEvent(dateEvent);
        eventGetDTO.setPlace(place);
    }

    @Test
    void getEventById_shouldReturnEventGetDTO() {
        Event event = new Event();
        event.setId(1L);
        EventGetDTO eventGetDTO = new EventGetDTO();
        eventGetDTO.setId(1L);

        Place place = new Place();
        place.setId(1L);

        Optional<Event> optionalEvent = Optional.of(event);
        when(eventRepository.findEventById(1L)).thenReturn(optionalEvent);
        when(mapper.convertValue(eq(optionalEvent), eq(EventGetDTO.class))).thenReturn(eventGetDTO); // Ajustar el stubbing

        when(placeRepository.getPlaceById(1L)).thenReturn(Optional.of(place));

        Optional<EventGetDTO> result = eventService.getEventById(1L);

        assertTrue(result.isPresent());
        assertEquals(eventGetDTO, result.get());
        assertEquals(place, result.get().getPlace());

        verify(eventRepository).findEventById(1L);
        verify(mapper).convertValue(eq(optionalEvent), eq(EventGetDTO.class)); // Verificar con el Optional<Event>
        verify(placeRepository).getPlaceById(1L);
    }





    @Test
    void getEventByName_shouldReturnEventGetDTO() {
        when(eventRepository.findEventByName("Event Name")).thenReturn(Optional.of(event));
        when(mapper.convertValue(eq(Optional.of(event)), eq(EventGetDTO.class))).thenReturn(eventGetDTO);
        when(placeRepository.getPlaceById(1L)).thenReturn(Optional.of(place));
        Optional<EventGetDTO> result = eventService.getEventByName("Event Name");
        assertTrue(result.isPresent());
        assertEquals(eventGetDTO, result.get());
        verify(eventRepository).findEventByName("Event Name");
        verify(mapper).convertValue(eq(Optional.of(event)), eq(EventGetDTO.class));
        verify(placeRepository).getPlaceById(1L);
    }




    @Test
    void getAllEvents_shouldReturnSetOfEventGetDTOs() {
        List<Event> events = Collections.singletonList(event);
        when(eventRepository.findAll()).thenReturn(events);
        when(mapper.convertValue(event, EventGetDTO.class)).thenReturn(eventGetDTO);

        Set<EventGetDTO> result = eventService.getAllEvents();

        assertEquals(1, result.size());
        assertTrue(result.contains(eventGetDTO));
        verify(eventRepository).findAll();
        verify(mapper).convertValue(event, EventGetDTO.class);
    }

//    @Test
//    void addEvent_shouldReturnSavedEventDTO() throws FileNotFoundException {
//        File file = ResourceUtils.getFile("classpath:testfile.txt");
//        byte[] content = Files.readAllBytes(file.toPath());
//        MockMultipartFile mockFile = new MockMultipartFile("file", "testfile.txt", "text/plain", content);
//        when(mapper.convertValue(eventDTO, Event.class)).thenReturn(event);
//        when(dateEventRepository.save(dateEvent)).thenReturn(dateEvent);
//        when(eventRepository.save(event)).thenReturn(event);
//        when(mapper.convertValue(event, EventDTO.class)).thenReturn(eventDTO);
//
//        EventDTO result = eventService.addEvent(eventDTO, file);
//
//        assertEquals(eventDTO, result);
//        verify(dateEventRepository).save(dateEvent);
//        verify(eventRepository).save(event);
//        verify(mapper).convertValue(eventDTO, Event.class);
//        verify(mapper).convertValue(event, EventDTO.class);
//    }

    @Test
    void updateEvent_shouldReturnUpdatedEventDTO() throws ResourceNotFoundException {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setName("Updated Event Name");
        eventDTO.setDescription("Updated Event Description");

        Event event = new Event();
        event.setId(1L);
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());

        when(eventRepository.findEventById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.convertValue(event, EventDTO.class)).thenReturn(eventDTO);

        EventDTO result = eventService.updateEvent(eventDTO);

        assertEquals(eventDTO, result);

        verify(eventRepository).findEventById(1L);
        verify(eventRepository).save(event);
    }


    @Test
    void deleteEventById_shouldInvokeRepositoryDeleteById() {
        eventService.deleteEventById(1L);

        verify(eventRepository).deleteById(1L);
    }

    @Test
    void deleteEventByName_shouldInvokeRepositoryDelete() {
        when(eventRepository.findEventByName("Event Name")).thenReturn(Optional.of(event));

        eventService.deleteEventByName("Event Name");

        verify(eventRepository).delete(event);
    }

    @Test
    void findByPlaceId_shouldReturnListOfEvents() {
        List<Event> events = Collections.singletonList(event);
        when(eventRepository.findByPlaceId(1L)).thenReturn(events);

        List<Event> result = eventService.findByPlaceId(1L);

        assertEquals(1, result.size());
        assertTrue(result.contains(event));
        verify(eventRepository).findByPlaceId(1L);
    }
    @Test
    public void testSearchEventsReport_WithEventName() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("eventName", "Sample Event");

        Event sampleEvent = new Event();
        sampleEvent.setId(1L);
        when(eventRepository.findEventByName("Sample Event")).thenReturn(Optional.of(sampleEvent));
        when(eventRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(sampleEvent));

        List<Long> result = eventService.searchEventsReport(criteria);

        assertEquals(Collections.singletonList(1L), result);
    }

    @Test
    public void testSearchEventsReport_WithCategory() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("category", "Sample Category");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        when(categoryService.getCategoryByName("Sample Category")).thenReturn(Optional.of(categoryDTO));

        Event sampleEvent = new Event();
        sampleEvent.setId(2L);
        when(eventRepository.findByCategory(1L)).thenReturn(Collections.singletonList(sampleEvent));
        when(eventRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(sampleEvent));

        List<Long> result = eventService.searchEventsReport(criteria);

        assertEquals(Collections.singletonList(2L), result);
    }

    @Test
    public void testSearchEventsReport_WithCity() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("city", "Sample City");

        Place samplePlace = new Place();
        samplePlace.setId(1L);
        when(placeRepository.getPlaceByCity("Sample City")).thenReturn(Collections.singleton(samplePlace));

        Event sampleEvent = new Event();
        sampleEvent.setId(3L);
        when(eventRepository.findByPlaceId(1L)).thenReturn(Collections.singletonList(sampleEvent));
        when(eventRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(sampleEvent));

        List<Long> result = eventService.searchEventsReport(criteria);

        assertEquals(Collections.singletonList(3L), result);
    }

    @Test
    public void testSearchEventsReport_WithPlace() {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("place", "Sample Place");

        Place samplePlace = new Place();
        samplePlace.setId(1L);
        when(placeRepository.getPlaceByName("Sample Place")).thenReturn(Optional.of(samplePlace));

        Event sampleEvent = new Event();
        sampleEvent.setId(4L);
        when(eventRepository.findByPlaceId(1L)).thenReturn(Collections.singletonList(sampleEvent));
        when(eventRepository.findAll(any(Specification.class))).thenReturn(Collections.singletonList(sampleEvent));

        List<Long> result = eventService.searchEventsReport(criteria);

        assertEquals(Collections.singletonList(4L), result);
    }
}

