package com.bookmyshow.userservice.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({
        "id",
        "role",
        "description"
})
public class RoleResponseDTO implements Serializable {

    private Long id;

    private String role;

    private String description;

    public RoleResponseDTO() {
    }

    public RoleResponseDTO(Long id, String role, String description) {
        this.id = id;
        this.role = role;
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoleResponseDTO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", description='" + description + '\'' +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
