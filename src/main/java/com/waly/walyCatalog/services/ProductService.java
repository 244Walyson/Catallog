package com.waly.walyCatalog.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.Repositories.ProductRepository;
import com.waly.walyCatalog.controllers.ProductController;
import com.waly.walyCatalog.dto.CategoryDTO;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.dto.UriDTO;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.projections.ProductsProjection;
import com.waly.walyCatalog.services.Exceptions.DatabaseException;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x).add((Iterable<Link>) linkTo(methodOn(ProductController.class).findAll(null, null, null)).withSelfRel())
                .add(linkTo(methodOn(ProductController.class).findById(x.getId())).withRel("Get Product by id")));
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
        prod.setDescription(dto.getDescription());
        prod.setPrice(dto.getPrice());
        prod.setImgUrl(dto.getImgUrl());
        for (CategoryDTO cat : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(cat.getId());
            prod.addCategory(category);
        }
        return prod;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable, String name, List<Long> ids) {
    Page<ProductsProjection> page = repository.searchProducts(pageable,name, ids);
    List<Long> productIds = page.map(x -> x.getId()).toList();
    List<Product> products = repository.searchProductsWithCategories(productIds);
    List<ProductDTO> productDTOS = products.stream().map(p -> new ProductDTO(p, p.getCategories())).toList();

    Page<ProductDTO> pageDTO = new PageImpl<>(productDTOS, page.getPageable(), page.getTotalElements());
    return pageDTO;
    }


    public UriDTO uploadFile(MultipartFile file) {
        URL url = s3Service.uploadFile(file);
        return new UriDTO(url.toString());
    }
}
