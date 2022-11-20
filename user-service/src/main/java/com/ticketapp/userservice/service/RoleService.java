package com.ticketapp.userservice.service;

import com.ticketapp.userservice.dto.PermissionDTO;
import com.ticketapp.userservice.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO save(RoleDTO roleRequestDTO);
    List<RoleDTO> findAll();
    RoleDTO findById(Long id);
    List<PermissionDTO> findPermissionsById(Long id);
    void addPermissionToRole(Long id, Long permissionId);
    void deletePermissionToRole(Long id, Long permissionId);
    RoleDTO update(Long id, RoleDTO roleRequestDTO);
    void delete(Long id);
}
