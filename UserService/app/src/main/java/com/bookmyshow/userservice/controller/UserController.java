package com.bookmyshow.userservice.controller;

import com.bookmyshow.userservice.dto.UserRequestDTO;
import com.bookmyshow.userservice.dto.UserResponseDTO;
import com.bookmyshow.userservice.service.UserService;
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
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    @Autowired
    private UserService service;


    @Tag(name = "Users")
    @Operation(
            method = "POST",
            summary = "Create a new user",
            description = "Create a new user"
    )
    @ApiResponse(responseCode = "201",
            description = "successfully created a new resource user",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponseDTO.class)
            )
    )
    @PostMapping(path = "/users")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(service.save(userRequestDTO), HttpStatus.CREATED);
    }

    @Tag(name = "Users")
    @Operation(
            method = "GET",
            summary = "Get all users",
            description = "Get all users"
    )
    @GetMapping(path = "/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @Tag(name = "Users")
    @Operation(
            method = "GET",
            summary = "Get user by id",
            description = "Get user by id"
    )
    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @Tag(name = "Users")
    @Operation(
            method = "PUT",
            summary = "Update user by id",
            description = "Update user by id"
    )
    @PutMapping(path = "/users/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(service.update(id, userRequestDTO), HttpStatus.OK);
    }

    @Tag(name = "Users")
    @Operation(
            method = "DELETE",
            summary = "Delete user by id",
            description = "Delete user by id"
    )
    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Objects> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
