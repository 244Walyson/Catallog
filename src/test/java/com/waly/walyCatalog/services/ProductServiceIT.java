package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {

    @Autowired
    private ProductService service;
    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1l;
        nonExistingId = 1000l;
        countTotalProducts = 10l;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists(){
        service.delete(existingId);

        Assertions.assertEquals(countTotalProducts-1, repository.count());
    }
    @Test
    public void deleteShouldThrowNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Assertions.assertEquals(countTotalProducts, repository.count());
    }
    @Test
    public void findAllShouldReturnPageWhenPage0Size10() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductDTO> result = service.findAll(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }
    @Test
    public void findAllShouldReturnEmptyPageWhenPageDoesNotExists() {
        PageRequest pageRequest = PageRequest.of(50, 10);
        Page<ProductDTO> result = service.findAll(pageRequest);

        Assertions.assertTrue(result.isEmpty());
    }
    @Test
    public void findAllShouldReturnSortedPageWhenSortedByName() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
        Page<ProductDTO> result = service.findAll(pageRequest);

        Assertions.assertFalse(result.isEmpty());
    }
}
