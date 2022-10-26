package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "permissionCode",
        "permissionDescription"
})
public class PermissionResponseDTO {

    private Long id;
    private String code;
    private String permissionDescription;

    public PermissionResponseDTO() {
    }

    @Override
    public String toString() {
        return "PermissionResponseDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
