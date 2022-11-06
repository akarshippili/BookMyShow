package com.bookmyshow.locationservice.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CityRequestDTO {

    @NotBlank
    private String name;

    @NotNull
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
