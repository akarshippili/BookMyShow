package com.ticketapp.locationservice.dto;

public class CityResponseDTO {

    private Long cityId;
    private String name;
    private StateResponseDTO state;


    public CityResponseDTO() {
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateResponseDTO getState() {
        return state;
    }

    public void setState(StateResponseDTO state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "CityResponseDTO{" +
                "cityId=" + cityId +
                ", name='" + name + '\'' +
                ", state=" + state +
                '}';
    }
}
