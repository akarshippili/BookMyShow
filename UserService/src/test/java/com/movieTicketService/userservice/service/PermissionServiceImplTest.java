package com.movieTicketService.userservice.service;

import com.movieTicketService.userservice.dao.entity.Permission;
import com.movieTicketService.userservice.dao.repo.PermissionRepository;
import com.movieTicketService.userservice.dto.PermissionDTO;
import com.movieTicketService.userservice.exception.PermissionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Spy
    private ModelMapper modelMapper;

    private PermissionService service;

    @BeforeEach
    void setUp() {
        service = new PermissionServiceImpl(modelMapper, permissionRepository);
    }

    private List<Permission> buildPermissionList() {
        return List.of(
                new Permission(),
                new Permission()
        );
    }

    @Test
    void findAll() {

        //given
        List<Permission> permissionList = buildPermissionList();
        when(permissionRepository.findAll()).thenReturn(permissionList);

        //when
        service.findAll();

        //then
        verify(permissionRepository, times(1)).findAll();
        verify(modelMapper, times(permissionList.size())).map(any(Permission.class), eq(PermissionDTO.class));

    }

    @Test
    void save() {
        //given
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode("test");
        permissionDTO.setDescription("description");

        when(permissionRepository.save(any(Permission.class))).thenReturn(new Permission());

        //when
        service.save(permissionDTO);

        //then
        ArgumentCaptor<Permission> savedCaptor = ArgumentCaptor.forClass(Permission.class);
        verify(permissionRepository, times(1)).save(savedCaptor.capture());

        Permission savedPermission = savedCaptor.getValue();
        assertEquals(savedPermission.getCode(), permissionDTO.getCode());
        assertEquals(savedPermission.getDescription(), permissionDTO.getDescription());
    }

    @Test
    void findById_exists() {
        //given
        Permission permission = new Permission();
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        //when
        service.findById(1L);

        //then
        verify(permissionRepository, times(1)).findById(1L);
    }

    @Test
    void findById_not_exists() {
        //given
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        // when - then
        assertThrows(
                PermissionNotFoundException.class,
                () -> service.findById(1L)
        );
    }


    @Test
    void delete_id_exists() {

        //given
        Permission permission = new Permission();
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        //when
        service.delete(1L);
        //then
        verify(permissionRepository, times(1)).delete(any(Permission.class));
    }

    @Test
    void delete_id_not_exists() {

        //given
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        //when - then
        assertThrows(
                PermissionNotFoundException.class,
                () -> service.delete(1L)
        );
    }

    @Test
    void update_on_id_not_exists() {

        //given
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode("testCode");
        permissionDTO.setDescription("testDescription");
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());

        //when - then
        assertThrows(
                PermissionNotFoundException.class,
                () -> service.update(1L, permissionDTO)
        );
    }


    @Test
    void update_on_id_exists() {

        //given
        Permission permission = new Permission();
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode("testCode");
        permissionDTO.setDescription("testDescription");
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        //when
        service.update(1L, permissionDTO);

        //then
        verify(permissionRepository, times(1)).findById(1L);

        ArgumentCaptor<Permission> savedCaptor = ArgumentCaptor.forClass(Permission.class);
        verify(permissionRepository, times(1)).save(savedCaptor.capture());
        Permission savedPermission = savedCaptor.getValue();
        assertEquals(savedPermission.getCode(), permissionDTO.getCode());
        assertEquals(savedPermission.getDescription(), permissionDTO.getDescription());

    }






}