package com.bookmyshow.userservice.dto;

import com.bookmyshow.userservice.dao.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@JsonPropertyOrder({
        "id",
        "permissionCode",
        "permissionDescription"
})
public class PermissionDTO {

    private Long id;

    @NotBlank
    private String permissionCode;

    @NotBlank
    private String permissionDescription;

    @JsonIgnore
    private Set<RoleDTO> roles;

    public PermissionDTO() {
    }


    @Override
    public String toString() {
        return "PermissionDTO{" +
                "id=" + id +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                ", roles=" + roles +
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

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
