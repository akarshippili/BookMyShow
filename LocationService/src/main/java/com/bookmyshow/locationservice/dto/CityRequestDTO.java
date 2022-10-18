package com.bookmyshow.locationservice.dto;


import com.bookmyshow.locationservice.dao.entity.State;

public class CityRequestDTO {

    private String name;
    private State state;

    public CityRequestDTO() {
    }

    @Override
    public String toString() {
        return "CityRequestDTO{" +
                "name='" + name + '\'' +
                ", state=" + state +
                '}';
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
}
