package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@JsonPropertyOrder({
        "permissionCode",
        "permissionDescription"
})
public class PermissionRequestDTO implements Serializable {

    @NotBlank
    private String code;

    @NotBlank
    private String permissionDescription;

    public PermissionRequestDTO() {
    }

    @Override
    public String toString() {
        return "PermissionRequestDTO{" +
                "code='" + code + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

}
