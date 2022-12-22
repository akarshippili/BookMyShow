package com.ticketapp.locationservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketapp.locationservice.dto.CityResponseDTO;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = StateController.class)
class StateControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StateService service;

    @Autowired
    private StateController controller;

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
    void save() {
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
    void update() {}

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