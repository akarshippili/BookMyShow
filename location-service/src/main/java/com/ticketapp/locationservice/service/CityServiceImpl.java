package com.ticketapp.locationservice.service;

import com.ticketapp.locationservice.dao.entity.City;
import com.ticketapp.locationservice.dao.entity.State;
import com.ticketapp.locationservice.dao.repo.CityRepository;
import com.ticketapp.locationservice.dao.repo.StateRepository;
import com.ticketapp.locationservice.dto.CityRequestDTO;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.exception.CityNotFoundException;
import com.ticketapp.locationservice.exception.StateNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CityResponseDTO addCity(CityRequestDTO requestDTO) {
        Optional<State> optionalState = stateRepository.findById(requestDTO.getStateId());
        if(optionalState.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", requestDTO.getStateId()));

        City city = new City();
        city.setName(requestDTO.getName());
        city.setState(optionalState.get());
        city = repository.save(city);

        return modelMapper.map(city, CityResponseDTO.class);
    }

    @Override
    public CityResponseDTO getCityById(Long id) {
        Optional<City> optionalCity = repository.findById(id);
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found", id));

        return modelMapper.map(optionalCity.get(), CityResponseDTO.class);
    }

    @Override
    public List<CityResponseDTO> getAllCities() {
        List<City> cities = repository.findAll();

        return cities.
                stream()
                .map(city -> modelMapper.map(city, CityResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CityResponseDTO update(Long id, CityRequestDTO requestDTO) {
        Optional<City> optionalCity = repository.findById(id);
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found", id));

        Optional<State> optionalState = stateRepository.findById(requestDTO.getStateId());
        if(optionalState.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", requestDTO.getStateId()));

        City city = optionalCity.get();
        city.setName(requestDTO.getName());
        city.setState(optionalState.get());
        city = repository.save(city);

        return modelMapper.map(city, CityResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        Optional<City> optionalCity = repository.findById(id);
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found", id));

        repository.delete(optionalCity.get());
    }
}
