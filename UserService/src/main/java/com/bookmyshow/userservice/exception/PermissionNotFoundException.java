package com.bookmyshow.userservice.exception;

public class PermissionNotFoundException extends NotFoundException{

    public PermissionNotFoundException(Long id) {
        super(String.format("Permission with id: %d not found", id));
    }
}
