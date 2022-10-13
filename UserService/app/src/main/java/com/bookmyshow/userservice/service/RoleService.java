package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dao.entity.Role;
import com.bookmyshow.userservice.dao.repo.RoleRepository;
import com.bookmyshow.userservice.dto.RoleRequestDTO;
import com.bookmyshow.userservice.dto.RoleResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmyshow.userservice.exception.RoleNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository repository;

    public RoleResponseDTO addRole(RoleRequestDTO roleRequestDTO){
        Role roleEntity = modelMapper.map(roleRequestDTO, Role.class);
        roleEntity.setRole(roleRequestDTO.getRole().toUpperCase());

        Role savedRole = repository.save(roleEntity);
        return modelMapper.map(savedRole, RoleResponseDTO.class);
    }

    public List<RoleResponseDTO> getAllRoles(){
        List<Role> roles = repository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleResponseDTO.class))
                .collect(Collectors.toList());
    }

    public RoleResponseDTO getRoleById(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %d is not found", id));
        }
        return modelMapper.map(roleOptional.get(), RoleResponseDTO.class);
    }

    public RoleResponseDTO update(Long id, RoleRequestDTO roleRequestDTO){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %d is not found", id));
        }

        Role role = roleOptional.get();
        role.setRole(roleRequestDTO.getRole());
        role.setRoleDescription(roleRequestDTO.getRoleDescription());

        repository.save(role);
        return modelMapper.map(role, RoleResponseDTO.class);
    }

    public void delete(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %d is not found", id));
        }

        repository.delete(roleOptional.get());
    }

}
