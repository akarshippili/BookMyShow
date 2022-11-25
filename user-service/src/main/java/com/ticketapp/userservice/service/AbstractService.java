package com.ticketapp.userservice.service;

import org.modelmapper.ModelMapper;

public abstract class AbstractService {
    protected final ModelMapper modelMapper;

    public AbstractService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
