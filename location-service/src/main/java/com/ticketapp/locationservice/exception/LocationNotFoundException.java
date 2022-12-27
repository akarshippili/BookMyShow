package com.ticketapp.locationservice.exception;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(Long id) {
        super(String.format("Location with id %d not found", id));
    }
}
