package com.bookmyshow.userservice.exception;

public class NotFoundException extends MovieTicketException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

}
