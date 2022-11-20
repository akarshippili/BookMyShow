package com.ticketapp.locationservice.exception;

public class StateNotFoundException extends RuntimeException{
    public StateNotFoundException(String message) {
        super(message);
    }
}
