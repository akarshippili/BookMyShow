package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dao.entity.Permission;
import com.movieTicketService.userservice.dao.repo.PermissionRepository;
import com.movieTicketService.userservice.dto.PermissionDTO;
import com.movieTicketService.userservice.exception.PermissionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PermissionServiceImpl extends AbstractService implements PermissionService  {

    private PermissionRepository repository;


    public PermissionDTO save(PermissionDTO permissionDTO){
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        Permission savedPermission = repository.save(permission);

        return modelMapper.map(savedPermission, PermissionDTO.class);
    }

    public List<PermissionDTO> findAll(){
        List<Permission> permissions = repository.findAll();
        return permissions.stream().map(
                permission -> modelMapper.map(permission, PermissionDTO.class)
        ).collect(Collectors.toList());
    }

    public PermissionDTO findById(Long id){
        return modelMapper.map(getById(id), PermissionDTO.class);
    }

    public PermissionDTO update(Long id, PermissionDTO permissionRequestDTO){
        Permission permission = getById(id);
        permission.setCode(permissionRequestDTO.getCode());
        permission.setDescription(permissionRequestDTO.getDescription());
        repository.save(permission);

        return modelMapper.map(permission, PermissionDTO.class);
    }

    public void delete(Long id){
        repository.delete(getById(id));
    }

    private Permission getById(Long id){
        Optional<Permission> optionalPermission = repository.findById(id);
        if(optionalPermission.isEmpty()) throw new PermissionNotFoundException(id);
        return optionalPermission.get();
    }

}
