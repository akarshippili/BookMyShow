package com.bookmyshow.userservice.exception;

public class PermissionNotFoundException extends RuntimeException{
    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long id) {
        super(String.format("Permission with id: %d not found", id));
    }
}
