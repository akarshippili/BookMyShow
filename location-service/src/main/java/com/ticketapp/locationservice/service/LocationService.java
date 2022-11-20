package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dto.LocationRequestDTO;
import com.ticketapp.locationservice.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {

    LocationResponseDTO addLocation(LocationRequestDTO requestDTO);

    LocationResponseDTO getLocationById(Long id);

    List<LocationResponseDTO> getAllLocations();

    LocationResponseDTO update(Long id, LocationRequestDTO requestDTO);

    void delete(Long id);

}
