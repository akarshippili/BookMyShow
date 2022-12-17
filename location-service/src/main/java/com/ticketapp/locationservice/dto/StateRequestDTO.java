package com.ticketapp.locationservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StateRequestDTO {

    @NotBlank(message = "name of the state cannot be blank")
    private String name;

}
