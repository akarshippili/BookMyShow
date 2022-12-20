package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dao.entity.City;
import com.ticketapp.locationservice.dao.entity.State;
import com.ticketapp.locationservice.dao.repo.CityRepository;
import com.ticketapp.locationservice.dao.repo.StateRepository;
import com.ticketapp.locationservice.dto.CityRequestDTO;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.exception.CityNotFoundException;
import com.ticketapp.locationservice.exception.StateNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityServiceImplTest {

    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        cityService = new CityServiceImpl(modelMapper, stateRepository, cityRepository);
    }


    private List<City> buildCityList() {
        return Arrays.asList(new City(), new City(), new City());
    }


    @Test
    void findAll() {

        //given
        List<City> cities = buildCityList();
        when(cityRepository.findAll()).thenReturn(cities);

        //when
        List<CityResponseDTO> cityResponseDTOS = cityService.findAll();

        //then
        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
        assertEquals(cityResponseDTOS.size(), cities.size());
    }

    @Test
    void save() {

        // given
        State state = new State();
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(state));
        when(cityRepository.save(any(City.class))).thenReturn(new City());


        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        // when
        cityService.save(request);

        // then
        Mockito.verify(stateRepository, times(1)).findById(request.getStateId());
        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);
        Mockito.verify(cityRepository, Mockito.times(1)).save(cityArgumentCaptor.capture());

        City argument = cityArgumentCaptor.getValue();
        assertEquals(argument.getName(), argument.getName());
        assertEquals(argument.getState(), state);

    }


    @Test
    void save_state_dose_not_exists() {

        // given
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        // when - then
        assertThrows(StateNotFoundException.class , () -> cityService.save(request));
        Mockito.verify(stateRepository, times(1)).findById(request.getStateId());
    }


    @Test
    void findById() {

        // given
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(new City()));

        // when
        Long id = anyLong();
        cityService.findById(id);

        // then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(cityRepository, times(1)).findById(longArgumentCaptor.capture());
        assertEquals(longArgumentCaptor.getValue(), id);
    }


    @Test
    void findById_not_exists() {

        // given
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when - then
        assertThrows(CityNotFoundException.class, () -> cityService.findById(1L));
    }


    @Test
    void delete_when_exists() {

        // given
        City city = new City();
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        // when
        Long id = anyLong();
        cityService.delete(id);

        // then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(cityRepository, times(1)).findById(longArgumentCaptor.capture());
        assertEquals(longArgumentCaptor.getValue(), id);

        Mockito.verify(cityRepository, times(1)).delete(city);
    }

    @Test
    void delete_when_not_exists() {

        // given
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when - then
        assertThrows(CityNotFoundException.class, () -> cityService.delete(1L));
    }


    @Test
    void update(){
        // given
        State state = new State();
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(state));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(new City()));


        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        // when
        CityResponseDTO response = cityService.update(1L, request);

        // then
        Mockito.verify(stateRepository, times(1)).findById(request.getStateId());
        Mockito.verify(cityRepository, times(1)).findById(1L);

        ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);
        Mockito.verify(cityRepository, Mockito.times(1)).save(cityArgumentCaptor.capture());

        City argument = cityArgumentCaptor.getValue();
        assertEquals(argument.getName(), argument.getName());
        assertEquals(argument.getState(), state);
    }


    @Test
    void update_state_not_exists(){
        // given
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(new City()));


        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        // when
        assertThrows(StateNotFoundException.class  ,() -> cityService.update(1L, request));

        // then
        Mockito.verify(stateRepository, times(1)).findById(request.getStateId());
        Mockito.verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    void update_city_not_exists(){
        // given
        when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());

        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        // when
        assertThrows(CityNotFoundException.class  ,() -> cityService.update(1L, request));

        // then
        Mockito.verify(cityRepository, times(1)).findById(1L);
    }


}