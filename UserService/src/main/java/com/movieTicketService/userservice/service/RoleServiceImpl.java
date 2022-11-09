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

    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(@Autowired RoleRepository repository, @Autowired PermissionRepository permissionRepository) {
        this.repository = repository;
        this.permissionRepository = permissionRepository;
    }

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
        return modelMapper.map(getById(id), RoleResponseDTO.class);
    }

    public List<PermissionResponseDTO> findPermissionsById(Long id){
        Role role = getById(id);

        return role.getPermissions()
                .stream()
                .map(permission -> modelMapper.map(permission, PermissionResponseDTO.class))
                .collect(Collectors.toList());
    }

    public void addPermissionsToRole(Long id, List<Long> permissionIds){
        Set<Permission> permissions =  getPermissionsByIds(permissionIds);
        Role role = getById(id);
        permissions.forEach(permission -> role.getPermissions().add(permission));
        repository.save(role);
    }

    public void deletePermissionToRole(Long id, List<Long> permissionIds){
        Set<Permission> permissions = getPermissionsByIds(permissionIds);
        Role role = getById(id);
        permissions.forEach(permission -> role.getPermissions().remove(permission));
        repository.save(role);
    }



    public RoleResponseDTO update(Long id, RoleRequestDTO roleRequestDTO){
        Role role = getById(id);
        role.setRole(roleRequestDTO.getRole());
        role.setDescription(roleRequestDTO.getDescription());

        repository.save(role);
        return modelMapper.map(role, RoleResponseDTO.class);
    }

    public void delete(Long id){
        repository.delete(getById(id));
    }

    private Role getById(Long id){
        Optional<Role> optionalRole = repository.findById(id);
        if(optionalRole.isEmpty()) throw new RoleNotFoundException(id);
        return optionalRole.get();
    }

    private Set<Permission> getPermissionsByIds(List<Long> permissionIds){
        return permissionIds
                .stream()
                .map(permissionId -> {
                    Optional<Permission> optionalPermission =  permissionRepository.findById(permissionId);
                    if (optionalPermission.isEmpty()) throw new PermissionNotFoundException(permissionId);
                    return optionalPermission.get();
                })
                .collect(Collectors.toSet());
    }
}
