package com.ticketapp.locationservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.dto.StateRequestDTO;
import com.ticketapp.locationservice.dto.StateResponseDTO;
import com.ticketapp.locationservice.exception.StateNotFoundException;
import com.ticketapp.locationservice.exception.handler.ErrorDetails;
import com.ticketapp.locationservice.service.StateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StateController.class)
class StateControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StateService service;

    private static List<StateResponseDTO> getStateResponseDTOS() {
        return Arrays.asList(
                new StateResponseDTO(),
                new StateResponseDTO(),
                new StateResponseDTO(),
                new StateResponseDTO(),
                new StateResponseDTO()
        );
    }


    private static List<CityResponseDTO> getCityResponseDTOS() {
        return Arrays.asList(
                new CityResponseDTO(),
                new CityResponseDTO(),
                new CityResponseDTO(),
                new CityResponseDTO(),
                new CityResponseDTO()
        );
    }

    @Test
    void save() throws Exception {
        // given
        StateRequestDTO request = new StateRequestDTO();
        request.setName("New York");

        StateResponseDTO response = new StateResponseDTO();
        response.setName("New York");
        response.setStateId(1L);
        when(service.save(any(StateRequestDTO.class))).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                post("/api/v1/states")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(1)).save(request);

        StateResponseDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), StateResponseDTO.class);
        assertEquals(1L, responseDTO.getStateId());
        assertEquals("New York", responseDTO.getName());
    }

    @Test
    void save_not_valid_request() throws Exception {
        // given
        StateRequestDTO request = new StateRequestDTO();

        StateResponseDTO response = new StateResponseDTO();
        response.setName("New York");
        response.setStateId(1L);
        when(service.save(any(StateRequestDTO.class))).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                        post("/api/v1/states")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(0)).save(request);

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals("/api/v1/states", errorDetails.getPath());
        assertEquals("name of the state cannot be blank", errorDetails.getErrorMap().get("name"));
    }

    @Test
    void findAll() throws Exception {
        // given
        List<StateResponseDTO> stateResponseDTOS = getStateResponseDTOS();
        when(service.findAll()).thenReturn(stateResponseDTOS);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/states").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<StateResponseDTO> states = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(states.size(), stateResponseDTOS.size());
    }


    @Test
    void findById_valid_id() throws Exception {
        // given
        when(service.findById(anyLong())).thenReturn(new StateResponseDTO());

        // when - then
        mockMvc.perform(get("/api/v1/states/{id}", anyLong()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        verify(service, times(1)).findById(anyLong());
    }

    @Test
    void findById_invalid_id() throws Exception {
        // given
        when(service.findById(anyLong())).thenThrow(new StateNotFoundException(1L));

        // when - then
        MvcResult result = mockMvc.perform(get("/api/v1/states/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ErrorDetails error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals(error.getMessage(), String.format("State with id %d not found", 1L));
        assertEquals("/api/v1/states/1", error.getPath());
    }

    @Test
    void getCitiesByStateId() throws Exception {

        // given
        List<CityResponseDTO> cityResponseDTOS = getCityResponseDTOS();
        when(service.getCitiesByStateId(anyLong())).thenReturn(cityResponseDTOS);

        // when - then
        MvcResult result = mockMvc.perform(get("/api/v1/states/{id}/cities", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<CityResponseDTO> cities = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(cities.size(), cityResponseDTOS.size());
    }

    @Test
    void getCitiesByStateId_invalid_id() throws Exception {

        // given
        when(service.getCitiesByStateId(anyLong())).thenThrow(new StateNotFoundException(1L));

        // when - then
        MvcResult result = mockMvc.perform(get("/api/v1/states/{id}/cities", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals(errorDetails.getMessage(), String.format("State with id %d not found", 1L));
        assertEquals("/api/v1/states/1/cities", errorDetails.getPath());
    }

    @Test
    void update() throws Exception {

        // given
       StateRequestDTO request = new StateRequestDTO();
       request.setName("New York");

        StateResponseDTO response = new StateResponseDTO();
        response.setName("New York");
        response.setStateId(1L);
        when(service.save(any(StateRequestDTO.class))).thenReturn(response);

       when(service.update(anyLong(), any(StateRequestDTO.class))).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                put("/api/v1/states/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        StateResponseDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), StateResponseDTO.class);
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getStateId());
        assertEquals("New York", responseDTO.getName());
    }
    @Test
    void update_bad_request() throws Exception {

        // given
        StateRequestDTO request = new StateRequestDTO();

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/states/{id}", 1L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(0)).update(anyLong(), any());

        ErrorDetails responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertNotNull(responseDTO);
        assertEquals("/api/v1/states/1", responseDTO.getPath());
        assertEquals("name of the state cannot be blank", responseDTO.getErrorMap().get("name"));
    }

    @Test
    void update_not_found_id() throws Exception {

        // given
        StateRequestDTO request = new StateRequestDTO();
        request.setName("New York");

        when(service.update(anyLong(), any())).thenThrow(new StateNotFoundException(1L));

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/states/{id}", 1L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(1)).update(anyLong(), any());

        ErrorDetails responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertNotNull(responseDTO);
        assertEquals("/api/v1/states/1", responseDTO.getPath());
        assertEquals("State with id 1 not found", responseDTO.getMessage());
    }

    @Test
    void delete_valid_id() throws Exception {

        // when - then
        mockMvc.perform(delete("/api/v1/states/{id}", anyLong()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(anyLong());
    }


    @Test
    void delete_not_valid_id() throws Exception {
        // given
        doThrow(new StateNotFoundException(1L)).when(service).delete(anyLong());

        // when - then
        MvcResult result =   mockMvc.perform(delete("/api/v1/states/{id}", anyLong()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);

        assertEquals(errorDetails.getMessage(), String.format("State with id %d not found", 1L));
        verify(service, times(1)).delete(anyLong());
    }
}