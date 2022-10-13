package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "role",
        "roleDescription"
})
public class RoleResponseDTO {

    private Long id;

    private String role;

    private String roleDescription;

    public RoleResponseDTO() {
    }

    public RoleResponseDTO(Long id, String role, String roleDescription) {
        this.id = id;
        this.role = role;
        this.roleDescription = roleDescription;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
