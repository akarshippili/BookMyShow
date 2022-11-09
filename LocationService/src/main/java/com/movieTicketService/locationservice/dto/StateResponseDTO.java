package com.movieTicketService.locationservice.dto;

public class StateResponseDTO {

    private Long stateId;
    private String name;

    public StateResponseDTO() {
    }

    @Override
    public String toString() {
        return "StateResponseDTO{" +
                "stateId=" + stateId +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
