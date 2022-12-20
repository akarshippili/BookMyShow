package com.ticketapp.locationservice.controller;

import com.ticketapp.locationservice.dto.CityRequestDTO;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class CityController extends AbstractController {

    private final CityService service;

    public CityController(@Autowired CityService service) {
        this.service = service;
    }

    @PostMapping(path = "/cities")
    public ResponseEntity<CityResponseDTO> addState(@Valid @RequestBody CityRequestDTO requestBody) {
        CityResponseDTO response = service.save(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<List<CityResponseDTO>> getAllStates(){
        log.info("Request for get all cities");
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/cities/{id}")
    public ResponseEntity<CityResponseDTO> getStateById(@PathVariable Long id){
        log.info("Request for get city by id {}", id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
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
