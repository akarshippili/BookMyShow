package com.bookmyshow.userservice.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoleRequestDTO implements Serializable {

    @NotBlank(message = "Role must not be empty")
    private String role;

    private String roleDescription;

    public RoleRequestDTO(String role, String roleDescription) {
        this.role = role;
        this.roleDescription = roleDescription;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public String toString() {
        return "RoleRequestDTO{" +
                "role='" + role + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}
