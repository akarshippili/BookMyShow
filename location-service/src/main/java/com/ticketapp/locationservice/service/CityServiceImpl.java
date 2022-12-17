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
public class CityServiceImpl extends AbstractService implements CityService {

    private StateRepository stateRepository;

    private CityRepository repository;

    public CityServiceImpl(ModelMapper modelMapper, StateRepository stateRepository, CityRepository repository) {
        super(modelMapper);
        this.stateRepository = stateRepository;
        this.repository = repository;
    }

    @Override
    public CityResponseDTO addCity(CityRequestDTO requestDTO) {
        State state = stateById(requestDTO.getStateId());

        City city = new City();
        city.setName(requestDTO.getName());
        city.setState(state);
        city = repository.save(city);

        return modelMapper.map(city, CityResponseDTO.class);
    }

    @Override
    public CityResponseDTO getCityById(Long id) {
        return modelMapper.map(cityById(id), CityResponseDTO.class);
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
        City city = cityById(id);
        State state = stateById(requestDTO.getStateId());

        city.setName(requestDTO.getName());
        city.setState(state);
        city = repository.save(city);

        return modelMapper.map(city, CityResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        repository.delete(cityById(id));
    }
   
    private City cityById(Long id) {
        Optional<City> optionalCity = repository.findById(id);
        if(optionalCity.isEmpty()) throw new CityNotFoundException(String.format("City with id %d not found",id));
        return optionalCity.get();
    }

    private State stateById(Long id) {
        Optional<State> optionalState = stateRepository.findById(id);
        if(optionalState.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", id));
        return optionalState.get();
    }
}
