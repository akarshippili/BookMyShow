package com.bookmyshow.userservice.service;

import com.bookmyshow.userservice.dao.entity.Permission;
import com.bookmyshow.userservice.dao.repo.PermissionRepository;
import com.bookmyshow.userservice.dto.PermissionRequestDTO;
import com.bookmyshow.userservice.dto.PermissionResponseDTO;
import com.bookmyshow.userservice.exception.PermissionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends AbstractService implements PermissionService  {

    @Autowired
    private PermissionRepository repository;

    public PermissionResponseDTO save(PermissionRequestDTO permissionRequestDTO){
        Permission permission = modelMapper.map(permissionRequestDTO, Permission.class);
        Permission savedPermission = repository.save(permission);

        return modelMapper.map(savedPermission, PermissionResponseDTO.class);
    }

    public List<PermissionResponseDTO> findAll(){
        List<Permission> permissions = repository.findAll();
        return permissions.stream().map(
                permission -> modelMapper.map(permission, PermissionResponseDTO.class)
        ).collect(Collectors.toList());
    }

    public PermissionResponseDTO findById(Long id){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(id);

        return modelMapper.map(optionalPermission.get(), PermissionResponseDTO.class);
    }

    public PermissionResponseDTO update(Long id, PermissionRequestDTO permissionRequestDTO){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(id);

        Permission permission = optionalPermission.get();
        permission.setCode(permissionRequestDTO.getCode());
        permission.setPermissionDescription(permissionRequestDTO.getPermissionDescription());
        repository.save(permission);

        return modelMapper.map(permission, PermissionResponseDTO.class);
    }

    public void delete(Long id){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(id);

        repository.delete(optionalPermission.get());
    }

}
