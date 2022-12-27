package com.ticketapp.userservice.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class APIError {

    private String message;
    private String path;
    private Date timeStamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errorMap;

    public APIError() {
    }

    public APIError(String message, String path) {
        this.message = message;
        this.path = path;
        this.timeStamp = new Date();
    }

    public APIError(String message, String path, Map<String, String> errorMap) {
        this.message = message;
        this.path = path;
        this.timeStamp = new Date();
        this.errorMap = errorMap;
    }
}
