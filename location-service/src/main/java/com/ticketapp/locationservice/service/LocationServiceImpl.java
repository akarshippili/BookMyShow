package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dao.entity.City;
import com.ticketapp.locationservice.dao.entity.Location;
import com.ticketapp.locationservice.dao.repo.CityRepository;
import com.ticketapp.locationservice.dao.repo.LocationRepository;
import com.ticketapp.locationservice.dto.LocationRequestDTO;
import com.ticketapp.locationservice.dto.LocationResponseDTO;
import com.ticketapp.locationservice.exception.CityNotFoundException;
import com.ticketapp.locationservice.exception.LocationNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl extends AbstractService implements LocationService{

    private final CityRepository cityRepository;

    private final LocationRepository repository;

    public LocationServiceImpl(ModelMapper modelMapper, CityRepository cityRepository, LocationRepository repository) {
        super(modelMapper);
        this.cityRepository = cityRepository;
        this.repository = repository;
    }

    @Override
    public LocationResponseDTO save(LocationRequestDTO requestDTO) {
        City city = cityById(requestDTO.getCityId());

        Location location = new Location();
        location.setCity(city);
        location.setLandmark(requestDTO.getLandmark());
        location.setName(requestDTO.getName());
        location.setStreet(requestDTO.getStreet());
        location = repository.save(location);

        return modelMapper.map(location, LocationResponseDTO.class);
    }

    @Override
    public LocationResponseDTO findById(Long id) {
        return modelMapper.map(locationById(id), LocationResponseDTO.class);
    }

    @Override
    public List<LocationResponseDTO> findAll() {
        List<Location> locations = repository.findAll();

        return locations
                .stream()
                .map(location -> modelMapper.map(location, LocationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LocationResponseDTO update(Long id, LocationRequestDTO requestDTO) {

        Location location = locationById(id);
        City city = cityById(requestDTO.getCityId());

        location.setCity(city);
        location.setLandmark(requestDTO.getLandmark());
        location.setName(requestDTO.getName());
        location.setStreet(requestDTO.getStreet());
        location = repository.save(location);

        return modelMapper.map(location, LocationResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.delete(locationById(id));
    }

    private Location locationById(Long id){
        Optional<Location> optionalLocation = repository.findById(id);
        if(optionalLocation.isEmpty()) throw new LocationNotFoundException(String.format("Location with id %d not found", id));

        return optionalLocation.get();
    }

    private City cityById(Long id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found",id));
        return optionalCity.get();
    }
}
