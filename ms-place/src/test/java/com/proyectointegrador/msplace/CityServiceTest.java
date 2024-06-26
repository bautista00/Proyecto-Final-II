package com.proyectointegrador.msplace;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectointegrador.msplace.domain.City;
import com.proyectointegrador.msplace.domain.Place;
import com.proyectointegrador.msplace.domain.Seat;
import com.proyectointegrador.msplace.domain.Zone;
import com.proyectointegrador.msplace.dto.*;
import com.proyectointegrador.msplace.repository.ICityRepository;
import com.proyectointegrador.msplace.service.implement.CityService;
import com.proyectointegrador.msplace.service.implement.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CityServiceTest {

	@Mock
	private ICityRepository cityRepository;

	@Mock
	private PlaceService placeService;

	@Mock
	private ObjectMapper mapper;

	@InjectMocks
	private CityService cityService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getCityById_Success() {
		Long cityId = 1L;
		City city = new City();
		city.setId(cityId);
		city.setPlaces(new HashSet<>());

		CityDTO cityDTO = new CityDTO();
		cityDTO.setId(cityId);

		when(mapper.convertValue(city, CityDTO.class)).thenReturn(cityDTO);

		when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

		Optional<CityDTO> result = cityService.getCityById(cityId);

		assertTrue(result.isPresent());
		assertEquals(cityId, result.get().getId());
	}



	@Test
	void getCityById_NotFound() {
		Long cityId = 1L;
		when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

		Optional<CityDTO> result = cityService.getCityById(cityId);

		assertFalse(result.isPresent());
	}

	@Test
	void getCityByName_Success() {
		String cityName = "Test City";
		City city = new City();
		city.setName(cityName);
		Place place = new Place();
		place.setId(1L);
		place.setName("Place Name");
		place.setCity(city);
		Zone zone = new Zone();
		zone.setId(1L);
		zone.setPlace(place);
		Seat seat = new Seat();
		seat.setId(1L);
		seat.setZone(zone);
		Set<Seat> seats = new HashSet<>();
		seats.add(seat);
		zone.setSeats(seats);
		Set<Zone> zones = new HashSet<>();
		zones.add(zone);
		place.setZones(zones);
		Set<Place> places = new HashSet<>();
		places.add(place);
		city.setPlaces(places);


		SeatDTO seatDTO = new SeatDTO();
		seatDTO.setId(1L);
		HashSet<SeatDTO> seatDTOS = new HashSet<>();
		seatDTOS.add(seatDTO);

		ZoneDTO zoneDTO = new ZoneDTO();
		zoneDTO.setId(1L);
		HashSet<ZoneDTO> zoneDTOS = new HashSet<>();
		zoneDTOS.add(zoneDTO);

		PlaceDTO placeDTO = new PlaceDTO();
		placeDTO.setId(1L);
		HashSet<PlaceDTO> placeDTOS = new HashSet<>();
		placeDTOS.add(placeDTO);


		CityDTO cityDTO = new CityDTO();
		cityDTO.setName(cityName);





		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		when(mapper.convertValue(city, CityDTO.class)).thenReturn(cityDTO);
		when(mapper.convertValue(place, PlaceDTO.class)).thenReturn(placeDTO);
		when(mapper.convertValue(zone, ZoneDTO.class)).thenReturn(zoneDTO);
		when(mapper.convertValue(seat, SeatDTO.class)).thenReturn(seatDTO);



		Optional<CityDTO> result = cityService.getCityByName(cityName);

		assertTrue(result.isPresent());
		assertEquals(cityName, result.get().getName());
		assertNotNull(result.get().getPlaces());
	}


	@Test
	void getCityByName_NotFound() {
		String cityName = "Test City";
		when(cityRepository.findByName(cityName)).thenReturn(Optional.empty());

		Optional<CityDTO> result = cityService.getCityByName(cityName);

		assertFalse(result.isPresent());
	}

	@Test
	void getAllCities_Success() {
		City city1 = new City();
		City city2 = new City();

		city1.setPlaces(new HashSet<>());
		city2.setPlaces(new HashSet<>());

		when(cityRepository.findAll()).thenReturn(List.of(city1, city2));

		CityDTO cityDTO1 = new CityDTO();
		CityDTO cityDTO2 = new CityDTO();

		when(mapper.convertValue(city1, CityDTO.class)).thenReturn(cityDTO1);
		when(mapper.convertValue(city2, CityDTO.class)).thenReturn(cityDTO2);

		Set<CityDTO> result = cityService.getAllCities();

		assertEquals(2, result.size());
	}


	@Test
	void getCityByZipCode_Success() {
		String zipCode = "12345";
		City city1 = new City();
		City city2 = new City();
		city1.setPlaces(new HashSet<>());
		city2.setPlaces(new HashSet<>());

		when(cityRepository.findByZipCode(zipCode)).thenReturn(Set.of(city1, city2));

		CityDTO cityDTO1 = new CityDTO();
		CityDTO cityDTO2 = new CityDTO();
		when(mapper.convertValue(city1, CityDTO.class)).thenReturn(cityDTO1);
		when(mapper.convertValue(city2, CityDTO.class)).thenReturn(cityDTO2);

		Set<CityDTO> result = cityService.getCityByZipCode(zipCode);

		assertEquals(2, result.size());
	}


	@Test
	void addCity_Success() {
		CityDTO cityDTO = new CityDTO();
		City city = new City();
		when(mapper.convertValue(cityDTO, City.class)).thenReturn(city);
		when(mapper.convertValue(city, CityDTO.class)).thenReturn(cityDTO);
		when(cityRepository.save(city)).thenReturn(city);

		CityDTO result = cityService.addCity(cityDTO);

		assertNotNull(result);
	}

	@Test
	void updateCity_Success() {
		CityDTO cityDTO = new CityDTO();
		City city = new City();
		when(mapper.convertValue(cityDTO, City.class)).thenReturn(city);
		when(mapper.convertValue(city, CityDTO.class)).thenReturn(cityDTO);
		when(cityRepository.save(city)).thenReturn(city);

		CityDTO result = cityService.updateCity(cityDTO);

		assertNotNull(result);
	}

	@Test
	void deleteCityById_Success() {
		Long cityId = 1L;
		doNothing().when(cityRepository).deleteById(cityId);

		cityService.deleteCityById(cityId);

		verify(cityRepository, times(1)).deleteById(cityId);
	}

	@Test
	void deleteCityByName_Success() {
		String cityName = "Test City";
		City city = new City();
		when(cityRepository.findByName(cityName)).thenReturn(Optional.of(city));
		doNothing().when(cityRepository).delete(city);

		cityService.deleteCityByName(cityName);

		verify(cityRepository, times(1)).delete(city);
	}

	@Test
	void getAllPlacesByCityId_Success() {
		Long cityId = 1L;
		Set<PlaceOnlyDTO> placesDTO = new HashSet<>();
		when(placeService.getAllPlacesByCityId(cityId)).thenReturn(placesDTO);

		Set<PlaceOnlyDTO> result = cityService.getAllPlacesByCityId(cityId);

		assertNotNull(result);
	}

	@Test
	void getAllPlacesByCityName_Success() {
		String cityName = "Test City";
		Long cityId = 1L;
		CityDTO cityDTO = new CityDTO();
		cityDTO.setId(cityId);

		when(cityService.getCityByName(cityName)).thenReturn(Optional.of(cityDTO));

		Set<PlaceOnlyDTO> placesDTO = new HashSet<>();
		when(placeService.getAllPlacesByCityId(cityId)).thenReturn(placesDTO);

		Set<PlaceOnlyDTO> result = cityService.getAllPlacesByCityName(cityName);

		assertNotNull(result);
	}

}

