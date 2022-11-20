package com.ticketapp.userservice.service;

import com.ticketapp.userservice.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    PermissionDTO save(PermissionDTO permissionDTO);
    List<PermissionDTO> findAll();
    PermissionDTO findById(Long id);
    PermissionDTO update(Long id, PermissionDTO permissionDTO);
    void delete(Long id);

}
