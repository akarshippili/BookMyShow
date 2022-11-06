package com.bookmyshow.locationservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LocationRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String street;

    @NotBlank
    private String landmark;

    @NotNull
    private Long cityId;

    public LocationRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "LocationRequestDTO{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", landmark='" + landmark + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
