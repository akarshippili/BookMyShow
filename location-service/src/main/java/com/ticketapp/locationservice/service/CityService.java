package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dto.CityRequestDTO;
import com.ticketapp.locationservice.dto.CityResponseDTO;

import java.util.List;

public interface CityService {
    CityResponseDTO addCity(CityRequestDTO requestDTO);

    CityResponseDTO getCityById(Long id);

    List<CityResponseDTO> getAllCities();

    CityResponseDTO update(Long id, CityRequestDTO requestDTO);

    void delete(Long id);
}
