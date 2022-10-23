package com.bookmyshow.locationservice.service;

import com.bookmyshow.locationservice.dto.LocationRequestDTO;
import com.bookmyshow.locationservice.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {

    LocationResponseDTO addLocation(LocationRequestDTO requestDTO);

    LocationResponseDTO getLocationById(Long id);

    List<LocationResponseDTO> getAllLocations();

    LocationResponseDTO update(Long id, LocationRequestDTO requestDTO);

    void delete(Long id);

}
