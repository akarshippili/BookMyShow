package com.ticketapp.userservice.controller;

import com.ticketapp.userservice.dto.PermissionDTO;
import com.ticketapp.userservice.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class PermissionController extends AbstractController {

    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

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
                    schema = @Schema(implementation = PermissionDTO.class)
            )
    )
    @PostMapping(path = "/permissions")
    public ResponseEntity<PermissionDTO> addPermission(@Valid @RequestBody PermissionDTO permissionDTO){
        log.info("Adding permission with code {} description {}", permissionDTO.getCode(), permissionDTO.getDescription());
        return new ResponseEntity<>( service.save(permissionDTO) , HttpStatus.CREATED);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "GET",
            summary = "Get all permissions",
            description = "Get all permissions"
    )
    @GetMapping(path = "/permissions")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        log.info("Request for get all permissions");
        return new ResponseEntity<>( service.findAll() , HttpStatus.OK);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "GET",
            summary = "Get permission by id",
            description = "Get permission by id"
    )
    @GetMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionDTO> getAllPermissions(@PathVariable Long id) {
        log.info("Request for get permission by id {}", id);
        return new ResponseEntity<>( service.findById(id) , HttpStatus.OK);
    }

    @Tag(name = "Permissions")
    @Operation(
            method = "PUT",
            summary = "Update permission by id",
            description = "Update permission by id"
    )
    @PutMapping(path = "/permissions/{id}")
    public ResponseEntity<PermissionDTO> update(@PathVariable Long id, @Valid @RequestBody PermissionDTO permissionDTO){
        log.info("Request for update permission {} with code {} description {}", id, permissionDTO.getCode(), permissionDTO.getDescription());
        PermissionDTO updatedPermission = service.update(id, permissionDTO);
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
        log.info("Request to delete permission by id {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
