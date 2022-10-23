package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dto.PermissionRequestDTO;
import com.bookmyshow.userservice.dto.PermissionResponseDTO;
import com.bookmyshow.userservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @PostMapping(path = "/permissions")
    public ResponseEntity<PermissionResponseDTO> addPermission(@Valid @RequestBody PermissionRequestDTO permissionRequestDTO){
        return new ResponseEntity( service.save(permissionRequestDTO) , HttpStatus.CREATED);
    }

    @GetMapping(path = "/permissions")
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        return new ResponseEntity<>( service.findAll() , HttpStatus.OK);
    }

    @GetMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionResponseDTO> getAllPermissions(@PathVariable Long id) {
        return new ResponseEntity<>( service.findById(id) , HttpStatus.OK);
    }

    @PutMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PermissionRequestDTO permissionRequestDTO){
        PermissionResponseDTO updatedPermission = service.update(id, permissionRequestDTO);
        return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
    }

    @DeleteMapping(path = "/permissions/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
