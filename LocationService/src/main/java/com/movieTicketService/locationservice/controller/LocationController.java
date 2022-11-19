package com.movieTicketService.locationservice.controller;


import com.movieTicketService.locationservice.dto.LocationRequestDTO;
import com.movieTicketService.locationservice.dto.LocationResponseDTO;
import com.movieTicketService.locationservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LocationController extends AbstractController {

    private LocationService service;

    public LocationController(@Autowired LocationService service) {
        this.service = service;
    }

    @PostMapping(path = "/locations")
    public ResponseEntity<LocationResponseDTO> addLocation(@Valid @RequestBody LocationRequestDTO requestBody) {
        LocationResponseDTO response = service.addLocation(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/locations")
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations(){
        return new ResponseEntity<>(service.getAllLocations(), HttpStatus.OK);
    }

    @GetMapping(path = "/locations/{id}")
    public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable Long id){
        return new ResponseEntity<>(service.getLocationById(id), HttpStatus.OK);
    }


    @PutMapping(path = "/locations/{id}")
    public ResponseEntity<LocationResponseDTO> update(@PathVariable Long id, @Valid @RequestBody LocationRequestDTO requestBody){
        return new ResponseEntity<>(service.update(id, requestBody), HttpStatus.OK);
    }

    @DeleteMapping(path = "/locations/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
