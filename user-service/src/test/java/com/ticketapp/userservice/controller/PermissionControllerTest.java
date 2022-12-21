package com.ticketapp.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketapp.userservice.dto.PermissionDTO;
import com.ticketapp.userservice.exception.PermissionNotFoundException;
import com.ticketapp.userservice.exception.handler.APIError;
import com.ticketapp.userservice.service.PermissionService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PermissionController.class)
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PermissionService service;

    @InjectMocks
    private PermissionController permissionController;

    @Test
    void permissions_thenReturns200() throws Exception {

        when(service.findAll()).thenReturn(
                List.of(
                        new PermissionDTO(),
                        new PermissionDTO()
                )
        );

        MvcResult result = mockMvc
                .perform(get("/api/v1/permissions").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse().getContentType());
    }

    @Test
    void get_permission_valid_input_return200() throws Exception {
        when(service.findById(1L)).thenReturn(new PermissionDTO());

        mockMvc.perform(
                get("/api/v1/permissions/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void get_permission_invalid_input_return404() throws Exception {
        when(service.findById(1L)).thenThrow(new PermissionNotFoundException(1L));

        MvcResult result =  mockMvc
                .perform(get("/api/v1/permissions/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertEquals("Permission with id: 1 not found", error.getMessage());
        assertEquals("/api/v1/permissions/1", error.getPath());
    }

    @Test
    void delete_permission_valid_input_return200() throws Exception {

        mockMvc
                .perform(delete("/api/v1/permissions/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
    }

    @Test
    void delete_permission_invalid_input_return404() throws Exception {
        doThrow(new PermissionNotFoundException(1L)).when(service).delete(1L);

        MvcResult result =  mockMvc
                .perform(delete("/api/v1/permissions/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertEquals("Permission with id: 1 not found", error.getMessage());
        assertEquals("/api/v1/permissions/1", error.getPath());
    }

    @Test
    void post_permissions_valid_input() throws Exception {

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode("PER-001");
        permissionDTO.setDescription("permission description");

        when(service.save(any(PermissionDTO.class))).thenReturn(new PermissionDTO());

        mockMvc.perform(
                post("/api/v1/permissions")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(permissionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        verify(service, times(1)).save(any());
    }

    @Test
    void post_permissions_invalid_input() throws Exception {

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode(null);
        permissionDTO.setDescription("permission description");

        MvcResult result = mockMvc.perform(
                        post("/api/v1/permissions")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(permissionDTO))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertNotNull(error.getErrorMap());
        assertEquals("must not be blank", error.getErrorMap().get("code"));
    }

    @Test
    void put_permissions_invalid_id() throws Exception {
        when(service.update(any(), any())).thenThrow(new PermissionNotFoundException(1L));

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode("PER-001");
        permissionDTO.setDescription("permission description");

        MvcResult result =  mockMvc.perform(
                put("/api/v1/permissions/{id}", 1L)
                       .contentType(MediaType.APPLICATION_JSON_VALUE)
                       .content(objectMapper.writeValueAsString(permissionDTO))
                )
                .andExpect(status().isNotFound())
                .andReturn();

        verify(service, times(1)).update(any(), any());
        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);
        assertEquals("Permission with id: 1 not found", error.getMessage());
        assertEquals("/api/v1/permissions/1", error.getPath());
    }

    @Test
    void put_permissions_invalid_input() throws Exception {

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode(null);
        permissionDTO.setDescription("permission description");

        MvcResult result =  mockMvc.perform(
                        put("/api/v1/permissions/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(permissionDTO))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        APIError error = objectMapper.readValue(result.getResponse().getContentAsString(), APIError.class);

        assertNotNull(error.getErrorMap());
        assertEquals("must not be blank", error.getErrorMap().get("code"));
    }

    @Test
    void put_permissions_valid_input_valid_id() throws Exception {

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setCode("PER-001");
        permissionDTO.setDescription("permission description");

        when(service.update(any(), any())).thenReturn(permissionDTO);

        MvcResult result =  mockMvc.perform(
                        put("/api/v1/permissions/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(permissionDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        verify(service, times(1)).update(eq(1L), any(PermissionDTO.class));
    }


}