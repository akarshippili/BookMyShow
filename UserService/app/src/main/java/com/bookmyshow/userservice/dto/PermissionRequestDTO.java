package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonPropertyOrder({
        "code",
        "description"
})
public class PermissionRequestDTO implements Serializable {

    @NotBlank
    private String code;

    @NotBlank
    private String description;

    public PermissionRequestDTO() {
    }

    @Override
    public String toString() {
        return "PermissionRequestDTO{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
