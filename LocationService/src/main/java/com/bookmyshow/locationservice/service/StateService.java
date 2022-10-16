package com.bookmyshow.locationservice.service;

import com.bookmyshow.locationservice.dto.StateRequestDTO;
import com.bookmyshow.locationservice.dto.StateResponseDTO;

import java.util.List;

public interface StateService {

    StateResponseDTO addState(StateRequestDTO requestDTO);

    StateResponseDTO getStateById(Long id);

    List<StateResponseDTO> getAllStates();

    StateResponseDTO update(Long id, StateRequestDTO requestDTO);

    void delete(Long id);

}
