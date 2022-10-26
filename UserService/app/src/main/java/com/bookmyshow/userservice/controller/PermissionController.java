package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dto.PermissionRequestDTO;
import com.bookmyshow.userservice.dto.PermissionResponseDTO;
import com.bookmyshow.userservice.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class PermissionController {

    @Autowired
    private PermissionService service;

    @Tag(name = "Permissions")
    @Operation(
            method = "POST",
            summary = "Create a new  permission",
            description = "Create a new permission"
    )
    @ApiResponse(responseCode = "201",
            description = "successfully created a new resource permission",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = PermissionResponseDTO.class)
            )
    )
    @PostMapping(path = "/permissions")
    public ResponseEntity<PermissionResponseDTO> addPermission(@Valid @RequestBody PermissionRequestDTO permissionRequestDTO){
        return new ResponseEntity<>( service.save(permissionRequestDTO) , HttpStatus.CREATED);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "GET",
            summary = "Get all permissions",
            description = "Get all permissions"
    )
    @GetMapping(path = "/permissions")
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        return new ResponseEntity<>( service.findAll() , HttpStatus.OK);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "GET",
            summary = "Get permission by id",
            description = "Get permission by id"
    )
    @GetMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionResponseDTO> getAllPermissions(@PathVariable Long id) {
        return new ResponseEntity<>( service.findById(id) , HttpStatus.OK);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "PUT",
            summary = "Update permission by id",
            description = "Update permission by id"
    )
    @PutMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PermissionRequestDTO permissionRequestDTO){
        PermissionResponseDTO updatedPermission = service.update(id, permissionRequestDTO);
        return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "DELETE",
            summary = "Delete permission by id",
            description = "Delete permission by id"
    )
    @DeleteMapping(path = "/permissions/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
