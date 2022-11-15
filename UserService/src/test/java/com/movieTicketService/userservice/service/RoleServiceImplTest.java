package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dao.entity.Permission;
import com.movieTicketService.userservice.dao.entity.Role;
import com.movieTicketService.userservice.dao.repo.PermissionRepository;
import com.movieTicketService.userservice.dao.repo.RoleRepository;
import com.movieTicketService.userservice.dto.PermissionDTO;
import com.movieTicketService.userservice.dto.RoleDTO;
import com.movieTicketService.userservice.exception.PermissionNotFoundException;
import com.movieTicketService.userservice.exception.RoleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Spy
    private ModelMapper modelMapper;
    private  RoleService roleService;

    @Spy private Role role = new Role();

   @BeforeEach
    public void setUp() {
       roleService = new RoleServiceImpl(modelMapper, roleRepository, permissionRepository);
   }

    private List<Permission> buildPermissionList() {
        return List.of(
                new Permission(),
                new Permission()
        );
    }

    private List<Role> buildRoleList() {
       return List.of(
                new Role(),
                new Role()
        );
    }

    @Test
    void save() {
       RoleDTO roleDTO = new RoleDTO();
       roleDTO.setRole("testRole");
       roleDTO.setDescription("testDescription");

       when(roleRepository.save(any(Role.class))).thenReturn(new Role());

       // when
        roleService.save(roleDTO);

        // then
        ArgumentCaptor<Role> savedCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository, times(1)).save(savedCaptor.capture());

        Role role = savedCaptor.getValue();
        assertEquals(role.getRole(), roleDTO.getRole().toUpperCase());
        assertEquals(role.getDescription(), roleDTO.getDescription());
    }

    @Test
    void findAll() {
       List<Role> roles = buildRoleList();
       when(roleRepository.findAll()).thenReturn(roles);

       // when
        List<RoleDTO> roleDTOList = roleService.findAll();

        // then
        verify(roleRepository, times(1)).findAll();
        verify(modelMapper, times(roles.size())).map(any(Role.class), eq(RoleDTO.class));
        assertEquals(roleDTOList.size(), roles.size());

    }

    @Test
    void findById_id_not_exists() {
       //given
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // when - then
        assertThrows(RoleNotFoundException.class, () -> roleService.findById(1L));
    }


    @Test
    void findById_id_exists() {
        //given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));

        // when
        Object res =  roleService.findById(1L);

        // when - then
        verify(roleRepository, times(1)).findById(1L);
        assertTrue(res instanceof RoleDTO);
    }

    @Test
    void findPermissionsById() {
       //given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(role.getPermissions()).thenReturn(new HashSet<>(buildPermissionList()));

        // when
        List<PermissionDTO> res = roleService.findPermissionsById(1L);

        // when - then
        verify(roleRepository, times(1)).findById(1L);
        assertEquals(res.size(), buildPermissionList().size());
        assertTrue(res.get(0)  instanceof PermissionDTO);
    }

    @Test
    void addPermissionToRole_valid_permission_id() {

        //given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(role.getPermissions()).thenReturn(new HashSet<>(buildPermissionList()));
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(new Permission()));

        // when
        roleService.addPermissionToRole(1L, 1L);

        // when - then
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(any(Role.class));
        verify(permissionRepository, times(1)).findById(1L);
        assertEquals(role.getPermissions().size(), buildPermissionList().size() + 1);

    }

    @Test
    void addPermissionToRole_not_valid_permission_id() {

        //given
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        // when - then
        assertThrows(PermissionNotFoundException.class, () -> roleService.addPermissionToRole(1L, 1L));
    }

    @Test
    void deletePermissionToRole() {
        //given
        List<Permission> permissions = buildPermissionList();
        Permission permission = permissions.get(0);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(role.getPermissions()).thenReturn(new HashSet<>(permissions));
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        // when
        roleService.deletePermissionToRole(1L, 1L);

        // when - then
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(any(Role.class));
        verify(permissionRepository, times(1)).findById(1L);
        assertEquals(role.getPermissions().size(), buildPermissionList().size() - 1);
    }

    @Test
    void update_id_exists() {
       //given
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole("testRole");
        roleDTO.setDescription("testDescription");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));

       // when
       RoleDTO res = roleService.update(1L, roleDTO);

       // then
       verify(roleRepository, times(1)).findById(1L);
       verify(roleRepository, times(1)).save(any(Role.class));
       assertEquals(res.getRole(), roleDTO.getRole().toUpperCase());
       assertEquals(res.getDescription(), roleDTO.getDescription());
    }

    @Test
    void update_id_not_exists() {
       //given
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // when - then
        RoleDTO roleDTO = new RoleDTO();
        assertThrows(RoleNotFoundException.class, () -> roleService.update(1L, roleDTO));
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void delete_id_exists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));

        // when
        roleService.delete(1L);

        // then
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).delete(any(Role.class));
    }

    @Test
    void delete_id_not_exists() {
        // given
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        assertThrows(
                RoleNotFoundException.class,
                () -> roleService.delete(1L)
        );
    }
}