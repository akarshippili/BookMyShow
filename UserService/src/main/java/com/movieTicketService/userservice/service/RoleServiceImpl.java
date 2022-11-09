package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dao.entity.Permission;
import com.movieTicketService.userservice.dao.entity.Role;
import com.movieTicketService.userservice.dao.repo.PermissionRepository;
import com.movieTicketService.userservice.dao.repo.RoleRepository;
import com.movieTicketService.userservice.dto.PermissionResponseDTO;
import com.movieTicketService.userservice.dto.RoleRequestDTO;
import com.movieTicketService.userservice.dto.RoleResponseDTO;
import com.movieTicketService.userservice.exception.PermissionNotFoundException;
import com.movieTicketService.userservice.exception.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends AbstractService implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    public RoleResponseDTO save(RoleRequestDTO roleRequestDTO){
        Role roleEntity = modelMapper.map(roleRequestDTO, Role.class);
        roleEntity.setRole(roleRequestDTO.getRole().toUpperCase());

        Role savedRole = repository.save(roleEntity);
        return modelMapper.map(savedRole, RoleResponseDTO.class);
    }

    public List<RoleResponseDTO> findAll(){
        List<Role> roles = repository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleResponseDTO.class))
                .collect(Collectors.toList());
    }

    public RoleResponseDTO findById(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(id);
        }
        return modelMapper.map(roleOptional.get(), RoleResponseDTO.class);
    }

    public List<PermissionResponseDTO> findPermissionsById(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(id);
        }

        Role role = roleOptional.get();

        return role.getPermissions()
                .stream()
                .map(permission -> modelMapper.map(permission, PermissionResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void addPermissionsToRole(Long id, List<Long> permissionIds){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(id);
        }

        Set<Permission> permissions =  permissionIds
                .stream()
                .map(permissionId -> {
                    Optional<Permission> optionalPermission =  permissionRepository.findById(permissionId);
                    if (optionalPermission.isEmpty()) throw new PermissionNotFoundException(id);
                    return optionalPermission.get();
                })
                .collect(Collectors.toSet());

        Role role = roleOptional.get();
        permissions.forEach(permission -> role.getPermissions().add(permission));
        repository.save(role);
    }

    public void deletePermissionToRole(Long id, List<Long> permissionIds){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(id);
        }

        Set<Permission> permissions =  permissionIds
                .stream()
                .map(permissionId -> {
                    Optional<Permission> optionalPermission =  permissionRepository.findById(permissionId);
                    if (optionalPermission.isEmpty()) throw new PermissionNotFoundException(id);
                    return optionalPermission.get();
                })
                .collect(Collectors.toSet());

        Role role = roleOptional.get();
        permissions.forEach(permission -> role.getPermissions().remove(permission));
        repository.save(role);
    }



    public RoleResponseDTO update(Long id, RoleRequestDTO roleRequestDTO){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(id);
        }

        Role role = roleOptional.get();
        role.setRole(roleRequestDTO.getRole());
        role.setDescription(roleRequestDTO.getDescription());

        repository.save(role);
        return modelMapper.map(role, RoleResponseDTO.class);
    }

    public void delete(Long id){
        Optional<Role> roleOptional = repository.findById(id);
        if(roleOptional.isEmpty()) {
            throw new RoleNotFoundException(id);
        }

        repository.delete(roleOptional.get());
    }

}
