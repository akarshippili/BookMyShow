package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userRequestDTO);
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO update(Long id, UserDTO userRequestDTO);
    void delete(Long id);

}
