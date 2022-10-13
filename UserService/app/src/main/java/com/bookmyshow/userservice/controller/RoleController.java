package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dto.RoleRequestDTO;
import com.bookmyshow.userservice.dto.RoleResponseDTO;
import com.bookmyshow.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class RoleController {

    @Autowired
    private RoleService service;

    @PostMapping(path = "/roles")
    public ResponseEntity<RoleResponseDTO> addRole(@Valid @RequestBody RoleRequestDTO role){
        return new ResponseEntity(service.addRole(role), HttpStatus.CREATED);
    }

    @GetMapping(path = "/roles/{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable Long id) {
        return new ResponseEntity<>(service.getRoleById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        return new ResponseEntity<>(service.getAllRoles(), HttpStatus.OK);
    }

    @PutMapping(path = "/roles/{id}")
    public ResponseEntity<RoleResponseDTO> update(@PathVariable Long id, @RequestBody RoleRequestDTO roleRequestDTO){
        RoleResponseDTO updatedRole = service.update(id, roleRequestDTO);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping(path = "/roles/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
