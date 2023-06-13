package com.waly.walyCatalog.repositories;

import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.tests.Factory;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    private Long existingId;
    private Long nonExistingId;
    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);
        Optional<Product> result = repository.findById(1L);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void savaShouldPersistWithAutoincrementWhenIdIsNull(){
            Product product = Factory.createProduct();
            product = repository.save(product);
            Assertions.assertNotNull(product.getId());
            Assertions.assertEquals(1L, product.getId());
    }

    @Test
    public void findByIdShouldReturnAOptionalWithProductWhenIdExists(){
        Optional<Product> product = repository.findById(existingId);
        Assertions.assertTrue(product.isPresent());
    }
    @Test
    public void findByIdShouldReturnAEmptyOptionalWhenIdNotExists(){
        Optional<Product> product = repository.findById(nonExistingId);
        Assertions.assertTrue(product.isEmpty());
    }

}
