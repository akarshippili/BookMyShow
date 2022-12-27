package com.ticketapp.locationservice.exception;

public class StateNotFoundException extends RuntimeException{
    public StateNotFoundException(Long id) {
        super(String.format("State with id %d not found", id));
    }
}
