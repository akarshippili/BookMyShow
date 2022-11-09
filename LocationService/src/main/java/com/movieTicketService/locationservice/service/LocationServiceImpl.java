package com.movieTicketService.locationservice.service;

import com.movieTicketService.locationservice.dao.entity.City;
import com.movieTicketService.locationservice.dao.entity.Location;
import com.movieTicketService.locationservice.dao.repo.CityRepository;
import com.movieTicketService.locationservice.dao.repo.LocationRepository;
import com.movieTicketService.locationservice.dto.LocationRequestDTO;
import com.movieTicketService.locationservice.dto.LocationResponseDTO;
import com.movieTicketService.locationservice.exception.CityNotFoundException;
import com.movieTicketService.locationservice.exception.LocationNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements  LocationService{

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocationRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LocationResponseDTO addLocation(LocationRequestDTO requestDTO) {
        Optional<City> optionalCity = cityRepository.findById(requestDTO.getCityId());
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found", requestDTO.getCityId()));

        Location location = new Location();
        location.setCity(optionalCity.get());
        location.setLandmark(requestDTO.getLandmark());
        location.setName(requestDTO.getName());
        location.setStreet(requestDTO.getStreet());

        location = repository.save(location);

        return modelMapper.map(location, LocationResponseDTO.class);
    }

    @Override
    public LocationResponseDTO getLocationById(Long id) {
        Optional<Location> optionalLocation = repository.findById(id);
        if(optionalLocation.isEmpty()) throw new LocationNotFoundException(String.format("Location with id %d not found", id));

        return modelMapper.map(optionalLocation.get(), LocationResponseDTO.class);
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {

        List<Location> locations = repository.findAll();

        return locations
                .stream()
                .map(location -> modelMapper.map(location, LocationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponseDTO update(Long id, LocationRequestDTO requestDTO) {

        Optional<Location> optionalLocation = repository.findById(id);
        if(optionalLocation.isEmpty()) throw new LocationNotFoundException(String.format("Location with id %d not found", id));

        Optional<City> optionalCity = cityRepository.findById(requestDTO.getCityId());
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found", requestDTO.getCityId()));

        Location location = optionalLocation.get();
        location.setCity(optionalCity.get());
        location.setLandmark(requestDTO.getLandmark());
        location.setName(requestDTO.getName());
        location.setStreet(requestDTO.getStreet());

        location = repository.save(location);

        return modelMapper.map(location, LocationResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        Optional<Location> optionalLocation = repository.findById(id);
        if(optionalLocation.isEmpty()) throw new LocationNotFoundException(String.format("Location with id %d not found", id));

        Location location = optionalLocation.get();
        repository.delete(location);
    }
}
