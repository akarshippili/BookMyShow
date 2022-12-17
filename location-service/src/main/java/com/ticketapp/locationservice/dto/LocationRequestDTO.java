package com.ticketapp.locationservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LocationRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String street;

    @NotBlank
    private String landmark;

    @NotNull
    private Long cityId;
}
