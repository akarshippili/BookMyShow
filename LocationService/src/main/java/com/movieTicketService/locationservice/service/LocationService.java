package com.movieTicketService.locationservice.service;

import com.movieTicketService.locationservice.dto.LocationRequestDTO;
import com.movieTicketService.locationservice.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {

    LocationResponseDTO addLocation(LocationRequestDTO requestDTO);

    LocationResponseDTO getLocationById(Long id);

    List<LocationResponseDTO> getAllLocations();

    LocationResponseDTO update(Long id, LocationRequestDTO requestDTO);

    void delete(Long id);

}
