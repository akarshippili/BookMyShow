package com.ticketapp.locationservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LocationRequestDTO {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "street cannot be empty")
    private String street;

    @NotBlank(message = "landmark cannot be empty")
    private String landmark;

    @NotNull(message = "city cannot be empty")
    private Long cityId;
}
