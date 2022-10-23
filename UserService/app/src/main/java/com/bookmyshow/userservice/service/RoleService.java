package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dto.PermissionResponseDTO;
import com.bookmyshow.userservice.dto.RoleRequestDTO;
import com.bookmyshow.userservice.dto.RoleResponseDTO;

import java.util.List;

public interface RoleService {
    RoleResponseDTO save(RoleRequestDTO roleRequestDTO);
    List<RoleResponseDTO> findAll();
    RoleResponseDTO findById(Long id);
    List<PermissionResponseDTO> findPermissionsById(Long id);
    void addPermissionsToRole(Long id, List<Long> permissionIds);
    void deletePermissionToRole(Long id, List<Long> permissionIds);
    RoleResponseDTO update(Long id, RoleRequestDTO roleRequestDTO);
    void delete(Long id);
}
