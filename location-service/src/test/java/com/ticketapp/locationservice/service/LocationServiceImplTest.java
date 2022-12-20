package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dao.entity.City;
import com.ticketapp.locationservice.dao.entity.Location;
import com.ticketapp.locationservice.dao.repo.CityRepository;
import com.ticketapp.locationservice.dao.repo.LocationRepository;
import com.ticketapp.locationservice.dto.LocationRequestDTO;
import com.ticketapp.locationservice.dto.LocationResponseDTO;
import com.ticketapp.locationservice.exception.CityNotFoundException;
import com.ticketapp.locationservice.exception.LocationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {


    private LocationService locationService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private LocationRepository locationRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        this.locationService = new LocationServiceImpl(modelMapper, cityRepository, locationRepository);
    }

    private List<Location> getLocations() {
        return  Arrays.asList(new Location(), new Location(), new Location());
    }

    @Test
    void save() {
        // given
        City city = new City();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        LocationRequestDTO request = new LocationRequestDTO();
        request.setName("Roxy Cinema New York");
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        // when
        locationService.save(request);

        // then
        Mockito.verify(cityRepository, times(1)).findById(request.getCityId());
        ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
        Mockito.verify(locationRepository, Mockito.times(1)).save(locationArgumentCaptor.capture());

        Location argument = locationArgumentCaptor.getValue();
        assertEquals(argument.getName(), argument.getName());
        assertEquals(argument.getCity(), city);
        assertEquals(argument.getLandmark(), argument.getLandmark());
        assertEquals(argument.getStreet(), argument.getStreet());
        assertNull(argument.getLocationId());
    }

    @Test
    void save_city_not_exists() {
        // given
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        LocationRequestDTO request = new LocationRequestDTO();
        request.setName("Roxy Cinema New York");
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        // when
        assertThrows(CityNotFoundException.class, () -> locationService.save(request));

        // then
        Mockito.verify(cityRepository, times(1)).findById(request.getCityId());
    }

    @Test
    void findById() {
        // given
        Location location = new Location();
        location.setLocationId(1L);
        location.setName("New York City");
        location.setLandmark("Central Park");
        location.setStreet("Manhattan");
        location.setCity(new City());

        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        // when
        LocationResponseDTO locationResponseDTO  = locationService.findById(1L);

        // then
        verify(locationRepository, times(1)).findById(1L);
        assertNotNull(locationResponseDTO);
        assertEquals(locationResponseDTO.getName(), location.getName());
        assertEquals(locationResponseDTO.getLocationId(), location.getLocationId());
        assertEquals(locationResponseDTO.getStreet(), location.getStreet());

    }

    @Test
    void findById_not_exists() {
        // given
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(LocationNotFoundException.class, () -> locationService.findById(1L));

        // then
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void findAll() {
        // given
        when(locationRepository.findAll()).thenReturn(getLocations());

        // when
        locationService.findAll();

        // then
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void delete() {

        Location location = new Location();
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        // when
        locationService.delete(1L);

        // then
        verify(locationRepository, times(1)).findById(1L);
        verify(locationRepository, times(1)).delete(location);
    }


    @Test
    void delete_not_exists() {

        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(LocationNotFoundException.class, () -> locationService.delete(1L));

        // then
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void update() {

        // given
        City city = new City();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(new Location()));
        when(locationRepository.save(any())).thenReturn(new Location());


        LocationRequestDTO request = new LocationRequestDTO();
        request.setCityId(1L);
        request.setStreet("Manhattan");
        request.setLandmark("central park");
        request.setName("blah blah");

        // when
        LocationResponseDTO response = locationService.update(1L, request);

        // then
        ArgumentCaptor<Location> locationArgumentCaptor = ArgumentCaptor.forClass(Location.class);
        verify(locationRepository, times(1)).save(locationArgumentCaptor.capture());

        Location location = locationArgumentCaptor.getValue();
        assertNotNull(location);
        assertEquals(location.getCity(), city);
        assertEquals(location.getLandmark(), request.getLandmark());
        assertEquals(location.getName(), request.getName());
        assertEquals(location.getStreet(), request.getStreet());
    }


    @Test
    void update_location_not_exists(){
        // given
        when(locationRepository.findById(anyLong())).thenReturn(Optional.empty());

        LocationRequestDTO request = new LocationRequestDTO();
        request.setCityId(1L);
        request.setStreet("Manhattan");
        request.setLandmark("central park");
        request.setName("blah blah");

        // when
        assertThrows(LocationNotFoundException.class  ,() -> locationService.update(1L, request));

        // then
        Mockito.verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void update_city_not_exists(){
        // given
        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(new Location()));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        LocationRequestDTO request = new LocationRequestDTO();
        request.setCityId(1L);
        request.setStreet("Manhattan");
        request.setLandmark("central park");
        request.setName("blah blah");

        // when
        assertThrows(CityNotFoundException.class  ,() -> locationService.update(1L, request));

        // then
        Mockito.verify(cityRepository, times(1)).findById(1L);
    }
}