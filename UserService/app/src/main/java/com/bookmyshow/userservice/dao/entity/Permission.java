package com.bookmyshow.userservice.dao.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table(name = "PERMISSION")
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PERMISSION_CODE")
    private String permissionCode;

    @Column(name = "PERMISSION_DESCRIPTION")
    private String permissionDescription;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }
}
