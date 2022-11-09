package com.movieTicketService.locationservice.controller;

import com.movieTicketService.locationservice.dto.CityRequestDTO;
import com.movieTicketService.locationservice.dto.CityResponseDTO;
import com.movieTicketService.locationservice.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class CityController {

    @Autowired
    private CityService service;

    @PostMapping(path = "/cities")
    public ResponseEntity<CityResponseDTO> addState(@Valid @RequestBody CityRequestDTO requestBody) {
        CityResponseDTO response = service.addCity(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<List<CityResponseDTO>> getAllStates(){
        return new ResponseEntity<>(service.getAllCities(), HttpStatus.OK);
    }

    @GetMapping(path = "/cities/{id}")
    public ResponseEntity<CityResponseDTO> getStateById(@PathVariable Long id){
        return new ResponseEntity<>(service.getCityById(id), HttpStatus.OK);
    }


    @PutMapping(path = "/cities/{id}")
    public ResponseEntity<CityResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CityRequestDTO requestBody){
        return new ResponseEntity<>(service.update(id, requestBody), HttpStatus.OK);
    }

    @DeleteMapping(path = "/cities/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
