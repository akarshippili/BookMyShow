package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dto.PermissionRequestDTO;
import com.movieTicketService.userservice.dto.PermissionResponseDTO;

import java.util.List;

public interface PermissionService {
    PermissionResponseDTO save(PermissionRequestDTO permissionRequestDTO);
    List<PermissionResponseDTO> findAll();
    PermissionResponseDTO findById(Long id);
    PermissionResponseDTO update(Long id, PermissionRequestDTO permissionRequestDTO);
    void delete(Long id);

}
