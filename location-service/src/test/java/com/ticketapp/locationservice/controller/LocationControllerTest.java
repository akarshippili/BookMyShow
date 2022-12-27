package com.ticketapp.locationservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.dto.LocationRequestDTO;
import com.ticketapp.locationservice.dto.LocationResponseDTO;
import com.ticketapp.locationservice.exception.LocationNotFoundException;
import com.ticketapp.locationservice.exception.handler.ErrorDetails;
import com.ticketapp.locationservice.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = LocationController.class)
class LocationControllerTest {

    @MockBean
    private LocationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private List<LocationResponseDTO> buildLocationResponses(){
        return Arrays.asList(
                new LocationResponseDTO(),
                new LocationResponseDTO(),
                new LocationResponseDTO(),
                new LocationResponseDTO()
        );
    }

    @Test
    void save() throws Exception {
        // given
        LocationRequestDTO request = new LocationRequestDTO();
        request.setName("Roxy Cinema New York");
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        LocationResponseDTO response = new LocationResponseDTO();
        response.setLocationId(1L);
        response.setName("Roxy Cinema New York");
        response.setStreet("manhattan");
        response.setCity(new CityResponseDTO());
        response.setLandmark("central Park");
        when(service.save(any(LocationRequestDTO.class))).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                post("/api/v1/locations")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(1)).save(any());

        LocationResponseDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), LocationResponseDTO.class);
        assertEquals(responseDTO.getLocationId(), response.getLocationId());
        assertEquals(responseDTO.getName(), response.getName());
        assertEquals(responseDTO.getStreet(), response.getStreet());
        assertEquals(responseDTO.getCity(), response.getCity());
        assertEquals(responseDTO.getLandmark(), response.getLandmark());
    }

    @Test
    void save_invalid_input() throws Exception {
        // given
        LocationRequestDTO request = new LocationRequestDTO();
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        // when
        MvcResult result = mockMvc.perform(
                        post("/api/v1/locations")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ErrorDetails errorDetails = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ErrorDetails.class);

        assertNotNull(errorDetails.getErrorMap());
        assertEquals("/api/v1/locations", errorDetails.getPath());
        assertEquals("name cannot be empty", errorDetails.getErrorMap().get("name"));
    }

    @Test
    void findAll() throws Exception {
        // given
        List<LocationResponseDTO> locationResponseDTOS = buildLocationResponses();
        when(service.findAll()).thenReturn(locationResponseDTOS);

        MvcResult result = mockMvc.perform(get("/api/v1/locations").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<LocationResponseDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(locationResponseDTOS.size(), response.size());
        verify(service, times(1)).findAll();

    }

    @Test
    void findById() throws Exception {
        // given
        when(service.findById(anyLong())).thenReturn(new LocationResponseDTO());

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/locations/{id}", anyLong()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(1)).findById(anyLong());
    }


    @Test
    void findById_invalid_id() throws Exception {
        // given
        when(service.findById(anyLong())).thenThrow(new LocationNotFoundException(1L));

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/locations/{id}", anyLong()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(1)).findById(anyLong());

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals(errorDetails.getMessage(), String.format("Location with id %d not found", 1L));
        assertNull(errorDetails.getErrorMap());
    }

    @Test
    void update() throws Exception {
        // given
        LocationRequestDTO request = new LocationRequestDTO();
        request.setName("Roxy Cinema New York");
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        LocationResponseDTO response = new LocationResponseDTO();
        response.setLocationId(1L);
        response.setName("Roxy Cinema New York");
        response.setStreet("manhattan");
        response.setCity(new CityResponseDTO());
        response.setLandmark("central Park");
        when(service.update(anyLong(), any(LocationRequestDTO.class))).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/locations/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(1)).update(anyLong(), any());

        LocationResponseDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), LocationResponseDTO.class);
        assertEquals(responseDTO.getLocationId(), response.getLocationId());
        assertEquals(responseDTO.getName(), response.getName());
        assertEquals(responseDTO.getStreet(), response.getStreet());
        assertEquals(responseDTO.getCity(), response.getCity());
        assertEquals(responseDTO.getLandmark(), response.getLandmark());
    }

    @Test
    void update_bad_request() throws Exception {
        // given
        LocationRequestDTO request = new LocationRequestDTO();
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/locations/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(0)).update(anyLong(), any());

        ErrorDetails errorDetails = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ErrorDetails.class);

        assertNotNull(errorDetails.getErrorMap());
        assertEquals("/api/v1/locations/1", errorDetails.getPath());
        assertEquals("name cannot be empty", errorDetails.getErrorMap().get("name"));
    }

    @Test
    void update_invalid_id() throws Exception {
        // given
        LocationRequestDTO request = new LocationRequestDTO();
        request.setName("Roxy Cinema New York");
        request.setLandmark("Central Park");
        request.setStreet("manhattan");
        request.setCityId(1L);

        doThrow(new LocationNotFoundException(1L)).when(service).update(anyLong(), any(LocationRequestDTO.class));

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/locations/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // then
        verify(service, times(1)).update(anyLong(), any());

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals(errorDetails.getMessage(), String.format("Location with id %d not found", 1L));
    }

    @Test
    void delete_by_id() throws Exception {
        mockMvc.perform(delete("/api/v1/locations/{id}", anyLong()))
                .andExpect(status().isNoContent());
        verify(service, times(1)).delete(anyLong());
    }

    @Test
    void delete_by_invalid_id() throws Exception {
        // given
        doThrow(new LocationNotFoundException(1L)).when(service).delete(anyLong());

        // when
        MvcResult result =  mockMvc.perform(delete("/api/v1/locations/{id}", anyLong()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(1)).delete(anyLong());

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals(errorDetails.getMessage(), String.format("Location with id %d not found", 1L));
    }
}