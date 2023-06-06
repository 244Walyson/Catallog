package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.services.Exceptions.DatabaseException;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;


    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){
        List<Product> result = repository.findAll();
        return result.stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
      Product dto = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(dto, dto.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product prod = new Product();
        setDto(prod, dto);
        repository.save(prod);
        return new ProductDTO(prod);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try{
            Product prod = repository.getReferenceById(id);
            setDto(prod, dto);
            repository.save(prod);
            return new ProductDTO(prod);
        }
        catch (EntityNotFoundException e){
            throw new NotFoundException("id not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new NotFoundException("resource not found");
        }
        try{
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("integrity violation");
        }
    }

    public Product setDto(Product prod, ProductDTO dto){
        prod.setName(dto.getName());
        prod.setId(dto.getId());
        prod.setDescription(dto.getDescription());
        prod.setPrice(dto.getPrice());
        prod.setImgUrl(dto.getImgUrl());
        return prod;
    }
}
