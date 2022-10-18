package com.bookmyshow.locationservice.dto;


import com.bookmyshow.locationservice.dao.entity.State;

public class CityRequestDTO {

    private String name;
    private Long stateId;

    public CityRequestDTO() {
    }

    @Override
    public String toString() {
        return "CityRequestDTO{" +
                "name='" + name + '\'' +
                ", stateId=" + stateId +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
}
