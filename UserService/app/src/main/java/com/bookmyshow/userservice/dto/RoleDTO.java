package com.bookmyshow.userservice.dto;

import com.bookmyshow.userservice.dao.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@JsonPropertyOrder({
        "id",
        "role",
        "roleDescription"
})
public class RoleDTO {

    private Long id;

    @NotBlank
    private String role;

    @NotBlank
    private String roleDescription;


    private Set<PermissionDTO> permissions;

    @JsonIgnore
    private Set<User> users;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String role, String roleDescription, Set<PermissionDTO> permissions, Set<User> users) {
        this.id = id;
        this.role = role;
        this.roleDescription = roleDescription;
        this.permissions = permissions;
        this.users = users;
    }


    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                ", permissions=" + permissions +
                ", users=" + users +
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

    public Set<PermissionDTO> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDTO> permissions) {
        this.permissions = permissions;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
