package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.dto.CategoryDTO;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;


    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){
        List<Product> result = repository.findAll();
        return result.stream().map(x -> new ProductDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
      Product dto = repository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
        return new ProductDTO(dto);
    }
}
