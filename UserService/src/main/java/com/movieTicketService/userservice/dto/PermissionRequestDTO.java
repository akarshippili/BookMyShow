package com.movieTicketService.userservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PermissionRequestDTO implements Serializable {

    @NotBlank
    private String code;

    @NotBlank
    private String description;
}
