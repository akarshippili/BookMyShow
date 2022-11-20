package com.ticketapp.userservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
    protected ModelMapper modelMapper;

    public AbstractService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
