package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dao.entity.Role;
import com.bookmyshow.userservice.dao.repo.RoleRepository;
import com.bookmyshow.userservice.dto.RoleDTO;
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

    public RoleDTO addRole(RoleDTO roleDTO){
        Role roleEntity = modelMapper.map(roleDTO, Role.class);
        roleEntity.setRole(roleDTO.getRole().toUpperCase());

        Role savedRole = repository.save(roleEntity);
        return modelMapper.map(savedRole, RoleDTO.class);
    }

    public List<RoleDTO> getAllRoles(){
        List<Role> roles = repository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %d is not found", id));
        }
        return modelMapper.map(roleOptional.get(), RoleDTO.class);
    }

    public RoleDTO update(Long id, RoleDTO roleDTO){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %d is not found", id));
        }

        Role newRole = modelMapper.map(roleDTO, Role.class);
        newRole.setId(roleOptional.get().getId());
        repository.save(newRole);

        return modelMapper.map(newRole, RoleDTO.class);
    }

    public void remove(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %d is not found", id));
        }

        repository.delete(roleOptional.get());
    }

    public void remove(String role){
        Optional<Role> roleOptional = repository.findByRole(role);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(String.format("Role of %s is not found", role));
        }

        repository.delete(roleOptional.get());
    }

}
