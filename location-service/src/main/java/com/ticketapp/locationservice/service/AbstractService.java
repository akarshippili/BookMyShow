package com.ticketapp.locationservice.service;

import org.modelmapper.ModelMapper;

public class AbstractService {
    protected ModelMapper modelMapper;

    public AbstractService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
