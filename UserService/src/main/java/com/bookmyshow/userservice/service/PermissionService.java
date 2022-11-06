package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dto.PermissionRequestDTO;
import com.bookmyshow.userservice.dto.PermissionResponseDTO;

import java.util.List;

public interface PermissionService {
    PermissionResponseDTO save(PermissionRequestDTO permissionRequestDTO);
    List<PermissionResponseDTO> findAll();
    PermissionResponseDTO findById(Long id);
    PermissionResponseDTO update(Long id, PermissionRequestDTO permissionRequestDTO);
    void delete(Long id);

}
