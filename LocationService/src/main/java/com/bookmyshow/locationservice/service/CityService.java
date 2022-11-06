package com.bookmyshow.locationservice.service;

import com.bookmyshow.locationservice.dto.CityRequestDTO;
import com.bookmyshow.locationservice.dto.CityResponseDTO;
import java.util.List;

public interface CityService {
    CityResponseDTO addCity(CityRequestDTO requestDTO);

    CityResponseDTO getCityById(Long id);

    List<CityResponseDTO> getAllCities();

    CityResponseDTO update(Long id, CityRequestDTO requestDTO);

    void delete(Long id);
}
