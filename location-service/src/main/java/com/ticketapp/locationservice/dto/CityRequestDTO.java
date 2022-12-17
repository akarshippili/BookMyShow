package com.ticketapp.locationservice.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CityRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Long stateId;
}
