package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dto.PermissionDTO;
import com.movieTicketService.userservice.dto.RoleRequestDTO;
import com.movieTicketService.userservice.dto.RoleResponseDTO;

import java.util.List;

public interface RoleService {
    RoleResponseDTO save(RoleRequestDTO roleRequestDTO);
    List<RoleResponseDTO> findAll();
    RoleResponseDTO findById(Long id);
    List<PermissionDTO> findPermissionsById(Long id);
    void addPermissionsToRole(Long id, List<Long> permissionIds);
    void deletePermissionToRole(Long id, List<Long> permissionIds);
    RoleResponseDTO update(Long id, RoleRequestDTO roleRequestDTO);
    void delete(Long id);
}
