package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;

@JsonPropertyOrder({
        "permissionCode",
        "permissionDescription"
})
public class PermissionRequestDTO {

    @NotBlank
    private String permissionCode;

    @NotBlank
    private String permissionDescription;

    public PermissionRequestDTO() {
    }

    @Override
    public String toString() {
        return "PermissionRequestDTO{" +
                "permissionCode='" + permissionCode + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

}
