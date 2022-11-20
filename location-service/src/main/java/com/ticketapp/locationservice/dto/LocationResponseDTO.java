package com.ticketapp.locationservice.dto;

public class LocationResponseDTO {

    private Long locationId;
    private String name;
    private String street;
    private String landmark;
    private CityResponseDTO city;

    public LocationResponseDTO() {
    }

    @Override
    public String toString() {
        return "LocationResponseDTO{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", landmark='" + landmark + '\'' +
                ", city=" + city +
                '}';
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public CityResponseDTO getCity() {
        return city;
    }

    public void setCity(CityResponseDTO city) {
        this.city = city;
    }
}
