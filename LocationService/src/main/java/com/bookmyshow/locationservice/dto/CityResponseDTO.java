package com.bookmyshow.locationservice.dto;


import com.bookmyshow.locationservice.dao.entity.State;

public class CityResponseDTO {

    private Long cityId;
    private String name;
    private State state;


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

    public State getState() {
        return state;
    }

    public void setState(State state) {
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
