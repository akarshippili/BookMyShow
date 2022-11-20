package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.dto.StateRequestDTO;
import com.ticketapp.locationservice.dto.StateResponseDTO;

import java.util.List;

public interface StateService {

    StateResponseDTO addState(StateRequestDTO requestDTO);

    StateResponseDTO getStateById(Long id);

    List<StateResponseDTO> getAllStates();

    StateResponseDTO update(Long id, StateRequestDTO requestDTO);

    void delete(Long id);

    List<CityResponseDTO> getCitiesByStateId(Long id);

}
