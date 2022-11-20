package com.ticketapp.userservice.service;

import com.ticketapp.userservice.dao.entity.Permission;
import com.ticketapp.userservice.dao.repo.PermissionRepository;
import com.ticketapp.userservice.dto.PermissionDTO;
import com.ticketapp.userservice.exception.PermissionNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl extends AbstractService implements PermissionService  {

    private final PermissionRepository repository;

    @Autowired
    public PermissionServiceImpl(ModelMapper modelMapper, PermissionRepository repository) {
        super(modelMapper);
        this.repository = repository;
    }


    public PermissionDTO save(PermissionDTO permissionDTO){
        Permission permission = modelMapper.map(permissionDTO, Permission.class);
        Permission savedPermission = repository.save(permission);
        return modelMapper.map(savedPermission, PermissionDTO.class);
    }

    public List<PermissionDTO> findAll(){
        List<Permission> permissions = repository.findAll();
        return permissions.stream().map(
                permission -> modelMapper.map(permission, PermissionDTO.class)
        ).toList();
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
