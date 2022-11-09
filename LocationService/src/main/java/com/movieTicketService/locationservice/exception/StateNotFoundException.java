package com.movieTicketService.locationservice.exception;

public class StateNotFoundException extends RuntimeException{
    public StateNotFoundException(String message) {
        super(message);
    }
}
