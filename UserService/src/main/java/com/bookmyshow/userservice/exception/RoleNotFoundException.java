package com.bookmyshow.userservice.exception;

public class RoleNotFoundException extends NotFoundException{

    public RoleNotFoundException(Long id) {
        super(String.format("Role with id: %d not found", id));
    }

}
