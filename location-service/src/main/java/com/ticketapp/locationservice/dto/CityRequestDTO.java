package com.ticketapp.locationservice.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CityRequestDTO {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotNull
    private Long stateId;
}
