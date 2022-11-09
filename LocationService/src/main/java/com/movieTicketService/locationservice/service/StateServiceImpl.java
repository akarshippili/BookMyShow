package com.movieTicketService.locationservice.service;

import com.movieTicketService.locationservice.dao.entity.State;
import com.movieTicketService.locationservice.dao.repo.StateRepository;
import com.movieTicketService.locationservice.dto.CityResponseDTO;
import com.movieTicketService.locationservice.dto.StateRequestDTO;
import com.movieTicketService.locationservice.dto.StateResponseDTO;
import com.movieTicketService.locationservice.exception.StateNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StateResponseDTO> getAllStates(){
        List<State> states  = repository.findAll();
        return states.stream()
                .map(state -> modelMapper.map(state, StateResponseDTO.class))
                .collect(Collectors.toList());
    }


    public StateResponseDTO update(Long id, StateRequestDTO requestDTO) {
        Optional<State> stateOptional  = repository.findById(id);
        if(stateOptional.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", id));

        State state = stateOptional.get();
        state.setName(requestDTO.getName());
        state = repository.save(state);

        return modelMapper.map(state, StateResponseDTO.class);
    }

    public void delete(Long id) {
        Optional<State> stateOptional  = repository.findById(id);
        if(stateOptional.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", id));

        repository.delete(stateOptional.get());
    }

    public StateResponseDTO getStateById(Long id){
        Optional<State> stateOptional  = repository.findById(id);
        if(stateOptional.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", id));

        return modelMapper.map(stateOptional.get(), StateResponseDTO.class);
    }


    public StateResponseDTO addState(StateRequestDTO request){
        State state = modelMapper.map(request, State.class);
        state = repository.save(state);
        return modelMapper.map(state, StateResponseDTO.class);
    }


    public List<CityResponseDTO> getCitiesByStateId(Long id) {
        Optional<State> stateOptional =  repository.findById(id);
        if(stateOptional.isEmpty()) throw new StateNotFoundException(String.format("State with id %d not found", id));


        return stateOptional
                .get()
                .getCities()
                .stream()
                .map(city -> modelMapper.map(city, CityResponseDTO.class))
                .collect(Collectors.toList());
    }

}
