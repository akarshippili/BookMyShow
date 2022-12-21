package com.ticketapp.locationservice.controller;

import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.dto.StateRequestDTO;
import com.ticketapp.locationservice.dto.StateResponseDTO;
import com.ticketapp.locationservice.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StateController extends AbstractController {

    private final StateService service;

    public StateController(@Autowired StateService service) {
        this.service = service;
    }

    @PostMapping(path = "/states")
    public ResponseEntity<StateResponseDTO> addState(@Valid @RequestBody StateRequestDTO requestBody) {
        StateResponseDTO response = service.save(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/states")
    public ResponseEntity<List<StateResponseDTO>> getAllStates(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/states/{id}")
    public ResponseEntity<StateResponseDTO> getStateById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/states/{id}/cities")
    public ResponseEntity<List<CityResponseDTO>> getCitiesByStateId(@PathVariable Long id){
        return new ResponseEntity<>(service.getCitiesByStateId(id), HttpStatus.OK);
    }


    @PutMapping(path = "/states/{id}")
    public ResponseEntity<StateResponseDTO> update(@PathVariable Long id, @Valid @RequestBody StateRequestDTO requestBody){
        return new ResponseEntity<>(service.update(id, requestBody), HttpStatus.OK);
    }

    @DeleteMapping(path = "/states/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
