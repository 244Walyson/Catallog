package com.waly.walyCatalog.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import com.waly.walyCatalog.services.ProductService;
import com.waly.walyCatalog.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService service;

    private ProductDTO productDTO;
    private Product product;
    private PageImpl<ProductDTO> list;
    private Long existingId;
    private Long nonExistingId;
    private Long dependencyId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 10L;
        dependencyId = 2L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();

        list = new PageImpl<>(List.of(Factory.createProductDTO()));
        Mockito.when(service.findAll(Pageable.unpaged())).thenReturn(list);

        Mockito.when(service.findById(existingId)).thenReturn(productDTO);
        Mockito.when(service.findById(nonExistingId)).thenThrow(NotFoundException.class);

        Mockito.when(service.update(productDTO, existingId)).thenReturn(productDTO);
        Mockito.when(service.update(productDTO, nonExistingId)).thenThrow(NotFoundException.class);

        Mockito.doNothing().when(service).delete(existingId);
        Mockito.doThrow(NotFoundException.class).when(service).delete(nonExistingId);

        Mockito.when(service.insert(productDTO)).thenReturn(productDTO);
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnNotFoundExceptionWhenIdDoesNotExists() throws Exception{

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }
    @Test
    public void findByIdShouldReturnProductDtoWhenExistingId() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void deleteShoulDoNothingWhenIdExists() throws Exception {
        mockMvc.perform(delete("/products/{id}", existingId)).andExpect(status().isNoContent());
    }
    @Test
    public void deleteShoulThrowNotFoundExceptionWhenIdDoesNotExists() throws Exception {
        mockMvc.perform(delete("/products/{id}", nonExistingId)).andExpect(status().isNotFound());
    }
    @Test
    public void deleteShoulDatabaseExceptionWhenIsDependencyNotExists() throws Exception {
        mockMvc.perform(delete("/products/{id}", dependencyId)).andExpect(status().isNoContent());
    }
    @Test
    public void insertShouldReturnProductDTO() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }
}
