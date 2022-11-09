package com.movieTicketService.locationservice.service;

import com.movieTicketService.locationservice.dto.CityResponseDTO;
import com.movieTicketService.locationservice.dto.StateRequestDTO;
import com.movieTicketService.locationservice.dto.StateResponseDTO;

import java.util.List;

public interface StateService {

    StateResponseDTO addState(StateRequestDTO requestDTO);

    StateResponseDTO getStateById(Long id);

    List<StateResponseDTO> getAllStates();

    StateResponseDTO update(Long id, StateRequestDTO requestDTO);

    void delete(Long id);

    List<CityResponseDTO> getCitiesByStateId(Long id);

}
