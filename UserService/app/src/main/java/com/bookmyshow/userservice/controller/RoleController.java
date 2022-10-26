package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dto.PermissionResponseDTO;
import com.bookmyshow.userservice.dto.RoleRequestDTO;
import com.bookmyshow.userservice.dto.RoleResponseDTO;
import com.bookmyshow.userservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Roles")
    @Operation(
            method = "POST",
            summary = "Create a new role",
            description = "Create a new role"
    )
    @PostMapping(path = "/roles")
    public ResponseEntity<RoleResponseDTO> addRole(@Valid @RequestBody RoleRequestDTO role){
        return new ResponseEntity(service.save(role), HttpStatus.CREATED);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "GET",
            summary = "Get role by id",
            description = "Get role by id"
    )
    @GetMapping(path = "/roles/{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "GET",
            summary = "Get all permissions of a role by id",
            description = "Get all permissions of a role by id"
    )
    @GetMapping(path = "/roles/{id}/permissions")
    public ResponseEntity<List<PermissionResponseDTO>> getPermissionOfRole(@PathVariable Long id) {
        return new ResponseEntity<>(service.findPermissionsById(id), HttpStatus.OK);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "POST",
            summary = "add permissions to a role by id",
            description = "add permissions to a role by id"
    )
    @PostMapping(path = "/roles/{id}/permissions")
    public ResponseEntity<Object> addPermissionsToRole(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        service.addPermissionsToRole(id, permissionIds);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "DELETE",
            summary = "delete permissions to a role by id",
            description = "delete permissions to a role by id"
    )
    @DeleteMapping(path = "/roles/{id}/permissions")
    public ResponseEntity<Object> deletePermissionToRole(@PathVariable Long id, @RequestBody List<Long> permissionIds) {
        service.deletePermissionToRole(id, permissionIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "GET",
            summary = "Get all roles",
            description = "Get all roles"
    )
    @GetMapping(path = "/roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "PUT",
            summary = "Update role by id",
            description = "Update role by id"
    )
    @PutMapping(path = "/roles/{id}")
    public ResponseEntity<RoleResponseDTO> update(@PathVariable Long id, @RequestBody RoleRequestDTO roleRequestDTO){
        RoleResponseDTO updatedRole = service.update(id, roleRequestDTO);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @Tag(name = "Roles")
    @Operation(
            method = "DELETE",
            summary = "delete role by id",
            description = "delete role by id"
    )
    @DeleteMapping(path = "/roles/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
