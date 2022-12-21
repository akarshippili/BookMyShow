package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dao.entity.City;
import com.ticketapp.locationservice.dao.entity.State;
import com.ticketapp.locationservice.dao.repo.StateRepository;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.dto.StateRequestDTO;
import com.ticketapp.locationservice.dto.StateResponseDTO;
import com.ticketapp.locationservice.exception.StateNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateServiceImplTest {

    private StateService stateService;

    @Mock
    private StateRepository stateRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        stateService = new StateServiceImpl(modelMapper, stateRepository);
    }


    private List<State> buildStateList() {
        return Arrays.asList(
                new State(),
                new State(),
                new State(),
                new State()
        );
    }

    private List<City> buildCityList() {
        return Arrays.asList(
                new City(),
                new City(),
                new City(),
                new City()
        );
    }


    @Test
    void getAllStates() {
        // given
        List<State> stateList = buildStateList();
        when(stateRepository.findAll()).thenReturn(stateList);

        // when
        List<StateResponseDTO> stateResponseDTOS = stateService.findAll();

        // then
        verify(stateRepository, times(1)).findAll();
        assertEquals(stateResponseDTOS.size(), stateList.size());
        verify(modelMapper, times(stateList.size())).map(any(State.class), eq(StateResponseDTO.class));
    }

    @Test
    void delete() {
        State state = new State();
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(state));

        // when
        stateService.delete(1L);

        // then
        verify(stateRepository, times(1)).findById(1L);
        verify(stateRepository, times(1)).delete(state);
    }

    @Test
    void delete_not_exists() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(StateNotFoundException.class,() -> stateService.delete(1L));

        // then
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    void update() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(new State()));
        when(stateRepository.save(any())).thenReturn(new State());


        StateRequestDTO request = new StateRequestDTO();
        request.setName("blah blah");

        // when
        stateService.update(1L, request);

        // then
        ArgumentCaptor<State> stateArgumentCaptor = ArgumentCaptor.forClass(State.class);
        verify(stateRepository, times(1)).save(stateArgumentCaptor.capture());

        State state = stateArgumentCaptor.getValue();
        assertNotNull(state);
        assertEquals(state.getName(), request.getName());
    }


    @Test
    void update_not_exists() {
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(StateNotFoundException.class,() -> stateService.update(1L, new StateRequestDTO()));

        // then
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    void findById() {
        // given
        State state = new State();
        state.setName("Karnataka");
        state.setStateId(1L);
        when(stateRepository.findById(any())).thenReturn(Optional.of(state));

        // when
        StateResponseDTO response  = stateService.findById(1L);

        // then
        assertEquals(response.getStateId(), state.getStateId());
        assertEquals(response.getName(), state.getName());
        verify(stateRepository, times(1)).findById(anyLong());
    }

    @Test
    void findById_not_exists() {
        // given
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(StateNotFoundException.class, () -> stateService.findById(1L));

        // then
        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    void save() {

        // given
        StateRequestDTO requestDTO = new StateRequestDTO();
        requestDTO.setName("Karnataka");

        State state = new State();
        state.setName(requestDTO.getName());
        state.setStateId(1L);
        when(stateRepository.save(any(State.class))).thenReturn(state);

        // when
        StateResponseDTO response = stateService.save(requestDTO);

        // then
        ArgumentCaptor<State> argumentCaptor = ArgumentCaptor.forClass(State.class);
        verify(stateRepository, times(1)).save(argumentCaptor.capture());
        State stateArg = argumentCaptor.getValue();
        assertEquals(stateArg.getName(), requestDTO.getName());

        assertEquals(response.getStateId(), state.getStateId());
        assertEquals(response.getName(), state.getName());
    }

    @Test
    void getCitiesByStateId() {
        // given
        State state = mock(State.class);
        state.setName("Karnataka");
        state.setStateId(1L);
        when(stateRepository.findById(anyLong())).thenReturn(Optional.of(state));

        List<City> cityList = buildCityList();
        when(state.getCities()).thenReturn(cityList);


        // when
        List<CityResponseDTO> response = stateService.getCitiesByStateId(1L);

        // then
        verify(stateRepository, times(1)).findById(anyLong());
        verify(state, times(1)).getCities();
        assertEquals(response.size(), cityList.size());
    }


    @Test
    void getCitiesByStateId_id_not_exists() {
        // given
        when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        assertThrows(StateNotFoundException.class  ,() -> stateService.getCitiesByStateId(1L));

        // then
        verify(stateRepository, times(1)).findById(1L);
    }
}