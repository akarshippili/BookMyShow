package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dto.CityRequestDTO;
import com.ticketapp.locationservice.dto.CityResponseDTO;

import java.util.List;

public interface CityService {
    CityResponseDTO save(CityRequestDTO requestDTO);

    CityResponseDTO findById(Long id);

    List<CityResponseDTO> findAll();

    CityResponseDTO update(Long id, CityRequestDTO requestDTO);

    void delete(Long id);
}
