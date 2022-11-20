package com.ticketapp.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketapp.userservice.dto.UserDTO;
import com.ticketapp.userservice.exception.UserNotFoundException;
import com.ticketapp.userservice.exception.handler.APIError;
import com.ticketapp.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private UserController userController;


    @Test
    void users_thenReturns200() throws Exception {

        when(service.findAll()).thenReturn(
                List.of(
                        new UserDTO(),
                        new UserDTO()
                )
        );

        MvcResult result = mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    @Test
    void get_user_valid_input_return200() throws Exception {
        when(service.findById(1L)).thenReturn(new UserDTO());

        mockMvc.perform(
                get("/api/v1/users/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void get_user_invalid_input_return404() throws Exception {
        when(service.findById(1L)).thenThrow(new UserNotFoundException(1L));

        MvcResult result =  mockMvc.perform(
                        get("/api/v1/users/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertEquals("User with id: 1 not found", error.getMessage());
        assertEquals("/api/v1/users/1", error.getPath());
    }

    @Test
    void delete_user_valid_input_return200() throws Exception {

        mockMvc.perform(
                delete("/api/v1/users/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void delete_user_invalid_input_return404() throws Exception {
        doThrow(new UserNotFoundException(1L)).when(service).delete(1L);

        MvcResult result =  mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertEquals("User with id: 1 not found", error.getMessage());
        assertEquals("/api/v1/users/1", error.getPath());
    }

    @Test
    void post_user_valid_input() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("joe@example.com");
        userDTO.setFirstName("Joe");
        userDTO.setLastName("Doe");

        when(service.save(any(UserDTO.class))).thenReturn(new UserDTO());

        mockMvc.perform(
                        post("/api/v1/users")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        verify(service, times(1)).save(any());
    }

    @Test
    void post_user_invalid_input() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("");
        userDTO.setFirstName("Joe");
        userDTO.setLastName("Doe");

        MvcResult result = mockMvc.perform(
                        post("/api/v1/users")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertNotNull(error.getErrorMap());
        assertEquals("must not be blank", error.getErrorMap().get("email"));
    }

    @Test
    void put_user_invalid_id() throws Exception {
        when(service.update(any(), any())).thenThrow(new UserNotFoundException(1L));

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("joe@example.com");
        userDTO.setFirstName("Joe");
        userDTO.setLastName("Doe");

        MvcResult result =  mockMvc.perform(
                        put("/api/v1/users/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isNotFound())
                .andReturn();

        verify(service, times(1)).update(any(), any());
        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertEquals("User with id: 1 not found", error.getMessage());
        assertEquals("/api/v1/users/1", error.getPath());
    }

    @Test
    void put_user_invalid_input() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("joe@example.com");
        userDTO.setFirstName("");
        userDTO.setLastName("Doe");

        MvcResult result =  mockMvc.perform(
                        put("/api/v1/users/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);

        assertNotNull(error.getErrorMap());
        assertEquals("must not be blank", error.getErrorMap().get("firstName"));
    }

    @Test
    void put_user_valid_input_valid_id() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("joe@example.com");
        userDTO.setFirstName("Joe");
        userDTO.setLastName("Doe");

        when(service.update(any(), any())).thenReturn(userDTO);

        MvcResult result =  mockMvc.perform(
                        put("/api/v1/users/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userDTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        verify(service, times(1)).update(eq(1L), any(UserDTO.class));
    }



}