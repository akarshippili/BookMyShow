package com.ticketapp.userservice.service;

import com.ticketapp.userservice.dao.entity.Role;
import com.ticketapp.userservice.dao.entity.User;
import com.ticketapp.userservice.dao.repo.RoleRepository;
import com.ticketapp.userservice.dao.repo.UserRepository;
import com.ticketapp.userservice.dto.UserDTO;
import com.ticketapp.userservice.exception.UserNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper;

    private UserService userservice;

    @BeforeEach
    void setUp() {
        userservice = new UserServiceImpl(modelMapper, userRepository, roleRepository);
    }

    List<User> buildUserList(){
        return List.of(
                new User(),
                new User(),
                new User()
        );
    }

    @Test
    void save() {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("doe@example.com");
        userDTO.setRoleId(1L);

        Role role = new Role();
        role.setId(1L);

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(new User());

        //when
        userservice.save(userDTO);

        //then
        ArgumentCaptor<User> savedCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedCaptor.capture());
        User user = savedCaptor.getValue();
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getRoleId(), user.getRole().getId());
    }

    @Test
    void findAll() {

        //given
        when(userRepository.findAll()).thenReturn(buildUserList());

        //when
        List<UserDTO> users = userservice.findAll();

        //then
        int size = buildUserList().size();
        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(size)).map(any(User.class), eq(UserDTO.class));
        assertEquals(size, users.size());

    }

    @Test
    void findById_id_exists() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        //when
        userservice.findById(1L);

        //then
        verify(userRepository, times(1)).findById(1L);
    }


    @Test
    void findById_id_not_exists() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //when -- then
        assertThrows(UserNotFoundException.class, () -> userservice.findById(1L));
    }

    @Test
    void update() {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        // when
        userservice.update(1L, userDTO);

        //then
        verify(userRepository, times(1)).findById(1L);
        ArgumentCaptor<User> savedCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(savedCaptor.capture());

        User user = savedCaptor.getValue();

        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }

    @Test
    void delete() {

        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        //when
        userservice.delete(1L);

        //then
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(any(User.class));

    }
}