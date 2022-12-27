package com.ticketapp.locationservice.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(Long id){
        super(String.format("City with id %d not found", id));
    }
}
