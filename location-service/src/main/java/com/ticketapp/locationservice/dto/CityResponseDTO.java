package com.ticketapp.locationservice.dto;

import lombok.Data;

@Data
public class CityResponseDTO {

    private Long cityId;
    private String name;
    private StateResponseDTO state;
}
