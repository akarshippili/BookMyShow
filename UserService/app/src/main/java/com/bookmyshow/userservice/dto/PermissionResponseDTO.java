package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "permissionCode",
        "permissionDescription"
})
public class PermissionResponseDTO {

    private Long id;
    private String permissionCode;
    private String permissionDescription;

    public PermissionResponseDTO() {
    }

    @Override
    public String toString() {
        return "PermissionResponseDTO{" +
                "id=" + id +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
