package com.bookmyshow.locationservice.controller;

import com.bookmyshow.locationservice.dto.StateRequestDTO;
import com.bookmyshow.locationservice.dto.StateResponseDTO;
import com.bookmyshow.locationservice.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class StateController {

    @Autowired
    private StateService service;


    @PostMapping(path = "/states")
    public ResponseEntity<StateResponseDTO> addState(@Valid @RequestBody StateRequestDTO requestBody) {
        StateResponseDTO response = service.addState(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/states")
    public ResponseEntity<List<StateResponseDTO>> getAllStates(){
        return new ResponseEntity<>(service.getAllStates(), HttpStatus.OK);
    }

    @GetMapping(path = "/states/{id}")
    public ResponseEntity<StateResponseDTO> getStateById(@PathVariable Long id){
        return new ResponseEntity<>(service.getStateById(id), HttpStatus.OK);
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
