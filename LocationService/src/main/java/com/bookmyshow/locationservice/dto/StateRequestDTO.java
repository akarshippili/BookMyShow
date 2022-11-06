package com.bookmyshow.locationservice.dto;

import javax.validation.constraints.NotBlank;

public class StateRequestDTO {

    @NotBlank(message = "name of the state cannot be blank")
    private String name;

    @Override
    public String toString() {
        return "StateRequestDTO{" +
                "name='" + name + '\'' +
                '}';
    }

    public StateRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
