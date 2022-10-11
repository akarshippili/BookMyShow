package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dao.entity.Permission;
import com.bookmyshow.userservice.dao.repo.PermissionRepository;
import com.bookmyshow.userservice.dto.PermissionDTO;
import com.bookmyshow.userservice.exception.PermissionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PermissionRepository repository;

    public PermissionDTO addPermission(PermissionDTO permissionDTO){
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        Permission savedPermission = repository.save(permission);

        return modelMapper.map(savedPermission, PermissionDTO.class);
    }

    public List<PermissionDTO> getAllPermissions(){
        List<Permission> permissions = repository.findAll();
        return permissions.stream().map(
                permission -> modelMapper.map(permission, PermissionDTO.class)
        ).collect(Collectors.toList());
    }

    public PermissionDTO getPermissionById(Long id){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(String.format("Permission with id: %d not found", id));

        return modelMapper.map(optionalPermission.get(), PermissionDTO.class);
    }

    public PermissionDTO update(Long id, PermissionDTO permissionDTO){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(String.format("Permission with id: %d not found", id));

        Permission newPermission = modelMapper.map(permissionDTO, Permission.class);
        newPermission.setId(id);

        repository.save(newPermission);

        return modelMapper.map(newPermission, PermissionDTO.class);
    }

    public void delete(Long id){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(String.format("Permission with id: %d not found", id));

        repository.delete(optionalPermission.get());
    }

}
