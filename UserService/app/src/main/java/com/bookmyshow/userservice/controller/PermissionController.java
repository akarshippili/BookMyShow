package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dao.entity.Permission;
import com.bookmyshow.userservice.dto.PermissionDTO;
import com.bookmyshow.userservice.dto.RoleDTO;
import com.bookmyshow.userservice.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
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
    public ResponseEntity<PermissionDTO> addPermission(@Valid @RequestBody PermissionDTO permissionDTO){
        return new ResponseEntity( service.addPermission(permissionDTO) , HttpStatus.CREATED);
    }

    @GetMapping(path = "/permissions")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return new ResponseEntity<>( service.getAllPermissions() , HttpStatus.OK);
    }

    @GetMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionDTO> getAllPermissions(@PathVariable Long id) {
        return new ResponseEntity<>( service.getPermissionById(id) , HttpStatus.OK);
    }

    @PutMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionDTO> update(@PathVariable Long id, @Valid @RequestBody PermissionDTO permissionDTO){
        PermissionDTO updatedPermission = service.update(id, permissionDTO);
        return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
    }

}
