package com.ticketapp.locationservice.dto;

import lombok.Data;

@Data
public class LocationResponseDTO {

    private Long locationId;
    private String name;
    private String street;
    private String landmark;
    private CityResponseDTO city;
}
