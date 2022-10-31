package com.bookmyshow.userservice.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(Long id) {
        super(String.format("Role with id: %d not found", id));
    }

}
