package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dto.UserRequestDTO;
import com.bookmyshow.userservice.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO save(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> findAll();
    UserResponseDTO findById(Long id);
    UserResponseDTO update(Long id, UserRequestDTO userRequestDTO);
    void delete(Long id);

}
