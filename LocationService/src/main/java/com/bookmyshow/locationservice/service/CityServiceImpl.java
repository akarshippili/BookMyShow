package com.bookmyshow.locationservice.service;

import com.bookmyshow.locationservice.dao.entity.City;
import com.bookmyshow.locationservice.dao.repo.CityRepository;
import com.bookmyshow.locationservice.dto.CityRequestDTO;
import com.bookmyshow.locationservice.dto.CityResponseDTO;
import com.bookmyshow.locationservice.exception.CityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CityResponseDTO addCity(CityRequestDTO requestDTO) {
        City city = modelMapper.map(requestDTO, City.class);
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

        City city = optionalCity.get();
        city.setName(requestDTO.getName());
        city.setState(requestDTO.getState());

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
