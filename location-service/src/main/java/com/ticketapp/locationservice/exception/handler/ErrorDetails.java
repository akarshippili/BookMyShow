package com.ticketapp.locationservice.exception.handler;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ErrorDetails {

    private String message;
    private String path;
    private Date timeStamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errorMap;

    public ErrorDetails() {}

    public ErrorDetails(String message, String path) {
        this.message = message;
        this.path = path;
        this.timeStamp = new Date();
    }

    public ErrorDetails(String message, String path, Map<String, String> errorMap) {
        this.message = message;
        this.path = path;
        this.timeStamp = new Date();
        this.errorMap = errorMap;
    }
}
