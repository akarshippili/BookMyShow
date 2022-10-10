package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dto.RoleDTO;
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
    public ResponseEntity<RoleDTO> addRole(@Valid @RequestBody RoleDTO roleDTO){
        return new ResponseEntity(service.addRole(roleDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/roles/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable Long id) {
        return new ResponseEntity<>(service.getRoleById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return new ResponseEntity<>(service.getAllRoles(), HttpStatus.OK);
    }

    @PutMapping(path = "/roles/{id}")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody RoleDTO roleDTO){
        RoleDTO updatedRole = service.update(id, roleDTO);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping(path = "/roles/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable Long id){
        service.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
