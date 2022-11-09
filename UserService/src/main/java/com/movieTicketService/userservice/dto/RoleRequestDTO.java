package com.movieTicketService.userservice.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoleRequestDTO implements Serializable {

    @NotBlank(message = "Role must not be empty")
    private String role;

    private String description;

    public RoleRequestDTO(String role, String description) {
        this.role = role;
        this.description = description;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "RoleRequestDTO{" +
                "role='" + role + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
