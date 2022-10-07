package com.bookmyshow.userservice.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Table(name = "ROLE")
@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ROLE_PERMISSION_TABLE",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}
