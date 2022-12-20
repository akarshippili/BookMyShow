package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dto.LocationRequestDTO;
import com.ticketapp.locationservice.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {

    LocationResponseDTO save(LocationRequestDTO requestDTO);

    LocationResponseDTO findById(Long id);

    List<LocationResponseDTO> findAll();

    LocationResponseDTO update(Long id, LocationRequestDTO requestDTO);

    void delete(Long id);

}
