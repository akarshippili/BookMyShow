package com.ticketapp.locationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketapp.locationservice.dto.CityRequestDTO;
import com.ticketapp.locationservice.dto.CityResponseDTO;
import com.ticketapp.locationservice.dto.StateResponseDTO;
import com.ticketapp.locationservice.exception.CityNotFoundException;
import com.ticketapp.locationservice.exception.handler.ErrorDetails;
import com.ticketapp.locationservice.service.CityService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CityController.class)
class CityControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService service;

    private List<CityResponseDTO> getCityResponseDTOs(){
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
        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        CityResponseDTO response = new CityResponseDTO();
        StateResponseDTO stateResponseDTO = new StateResponseDTO();

        stateResponseDTO.setName("New York State");
        stateResponseDTO.setStateId(1L);

        response.setCityId(1L);
        response.setName("New York");
        response.setState(stateResponseDTO);

        when(service.save(any())).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                post("/api/v1/cities")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(1)).save(any());
        assertNotNull(result);

        CityResponseDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), CityResponseDTO.class);
        assertEquals(1L, responseDTO.getCityId());
        assertEquals("New York", responseDTO.getName());
        assertEquals(stateResponseDTO, responseDTO.getState());
    }


    @Test
    void save_bad_request() throws Exception {

        // given
        CityRequestDTO request = new CityRequestDTO();
        request.setStateId(1L);

        // when
        MvcResult result = mockMvc.perform(
                        post("/api/v1/cities")
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(0)).save(any());
        assertNotNull(result);

        ErrorDetails response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals("/api/v1/cities", response.getPath());
        assertEquals("name cannot be empty", response.getErrorMap().get("name"));
    }

    @Test
    void findAll() throws Exception {
        // given
        List<CityResponseDTO> cityResponseDTOS = getCityResponseDTOs();
        when(service.findAll()).thenReturn(cityResponseDTOS);

        // when
        MvcResult result = mockMvc.perform(get("/api/v1/cities").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        List<CityResponseDTO> cities = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(cities.size(), cityResponseDTOS.size());
    }

    @Test
    void findById() throws Exception {
        // given
        when(service.findById(anyLong())).thenReturn(new CityResponseDTO());

        // when - then
        mockMvc.perform(get("/api/v1/cities/{id}", anyLong()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        verify(service, times(1)).findById(anyLong());
    }

    @Test
    void findBy_invalid_id() throws Exception {
        // given
        when(service.findById(anyLong())).thenThrow(new CityNotFoundException(1L));

        // when - then
        MvcResult result = mockMvc.perform(get("/api/v1/cities/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(1)).findById(anyLong());

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals("/api/v1/cities/1", errorDetails.getPath());
        assertEquals("City with id 1 not found", errorDetails.getMessage());
        assertNull(errorDetails.getErrorMap());
    }

    @Test
    void update() throws Exception {

        // given
        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        CityResponseDTO response = new CityResponseDTO();
        StateResponseDTO stateResponseDTO = new StateResponseDTO();

        stateResponseDTO.setName("New York State");
        stateResponseDTO.setStateId(1L);

        response.setCityId(1L);
        response.setName("New York");
        response.setState(stateResponseDTO);

        when(service.update(any(), any())).thenReturn(response);

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/cities/{id}", 1L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ArgumentCaptor<CityRequestDTO> argumentCaptor = ArgumentCaptor.forClass(CityRequestDTO.class);
        verify(service, times(1)).update(anyLong(), argumentCaptor.capture());
        CityRequestDTO argument = argumentCaptor.getValue();
        assertEquals("New York", argument.getName());
        assertEquals(1L, argument.getStateId());


        assertNotNull(result);
        CityResponseDTO responseDTO = objectMapper.readValue(result.getResponse().getContentAsString(), CityResponseDTO.class);
        assertEquals(1L, responseDTO.getCityId());
        assertEquals("New York", responseDTO.getName());
        assertEquals(stateResponseDTO, responseDTO.getState());

    }

    @Test
    void update_bad_request() throws Exception {

        // given
        CityRequestDTO request = new CityRequestDTO();
        request.setStateId(1L);

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/cities/{id}", 1L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        verify(service, times(0)).update(anyLong(), any());

        assertNotNull(result);
        ErrorDetails response = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals("/api/v1/cities/1", response.getPath());
        assertEquals("name cannot be empty", response.getErrorMap().get("name"));

    }

    @Test
    void update_not_found() throws Exception {

        // given
        CityRequestDTO request = new CityRequestDTO();
        request.setName("New York");
        request.setStateId(1L);

        when(service.update(any(), any())).thenThrow(new CityNotFoundException(1L));

        // when
        MvcResult result = mockMvc.perform(
                        put("/api/v1/cities/{id}", 1L)
                                .content(objectMapper.writeValueAsBytes(request))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        ArgumentCaptor<CityRequestDTO> argumentCaptor = ArgumentCaptor.forClass(CityRequestDTO.class);
        verify(service, times(1)).update(anyLong(), argumentCaptor.capture());
        CityRequestDTO argument = argumentCaptor.getValue();
        assertEquals("New York", argument.getName());
        assertEquals(1L, argument.getStateId());


        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDetails.class);
        assertEquals("/api/v1/cities/1", errorDetails.getPath());
        assertEquals("City with id 1 not found", errorDetails.getMessage());
        assertNull(errorDetails.getErrorMap());

    }

    @Test
    void delete_valid_id() throws Exception {
        mockMvc.perform(
                delete("/api/v1/cities/{id}", anyLong())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNoContent());
        verify(service, times(1)).delete(anyLong());
    }


    @Test
    void delete_invalid_id() throws Exception {
        doThrow(new CityNotFoundException(1L)).when(service).delete(anyLong());

        MvcResult result = mockMvc.perform(delete("/api/v1/cities/{id}", anyLong()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(service, times(1)).delete(anyLong());

        ErrorDetails errorDetails = objectMapper.readValue(result.getResponse().getContentAsString() , ErrorDetails.class);
        assertNotNull(errorDetails);
        assertEquals("City with id 1 not found", errorDetails.getMessage());
        assertNull(errorDetails.getErrorMap());
    }
}