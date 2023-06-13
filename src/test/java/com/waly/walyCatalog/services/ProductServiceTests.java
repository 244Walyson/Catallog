package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.dto.CategoryDTO;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.services.Exceptions.DatabaseException;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import com.waly.walyCatalog.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PageImpl<Product> page;
    private Category category;
    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(product));
        category = Factory.createCategory();

        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(categoryRepository.getReferenceById(existingId)).thenReturn(category);
        Mockito.when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);


        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);
    }
    @Test
    public void updateShouldReturnProductWhenExistsId(){
        ProductDTO product = service.update(new ProductDTO(Factory.createProduct()), existingId);

        Assertions.assertNotNull(product);
    }
    @Test
    public void updateShouldTrhowNotFoundExceptionWhenNonExistingId(){
        Assertions.assertThrows(NotFoundException.class,()->{
            service.update(new ProductDTO(Factory.createProduct()), nonExistingId);
        });
    }
    @Test
    public void findByIdShouldReturnProductDTOWhenExistingId(){
        ProductDTO product = service.findById(existingId);

        Assertions.assertNotNull(product);
        Mockito.verify(repository, Mockito.times(1)).findById(existingId);
    }
    @Test
    public void findByIdShouldReturnNotFoundExceptionWhenNonExistingId(){
        Assertions.assertThrows(RuntimeException.class, ()->{
           service.findById(nonExistingId);
        });
    }
    @Test
    public void findAllShouldReturnPage(){
        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
    @Test
    public void deleteShouldTrhowDatabaseExceptionWhenDependentId(){
        Assertions.assertThrows(DatabaseException.class, ()->{
           service.delete(dependentId);
        });
    }

    @Test
    public void deleteShouldTrhowNotFoundExceptionWhenNonExistsId(){
        Assertions.assertThrows(NotFoundException.class, ()->{
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenNonExistsId(){
        Assertions.assertDoesNotThrow(()->{
            service.delete(existingId);
        });
    }

}
