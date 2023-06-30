package com.waly.walyCatalog.controllers;

import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.projections.ProductsProjection;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import com.waly.walyCatalog.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

//    @GetMapping
//    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
//        Page<ProductDTO> dto = service.findAll(pageable);
//        return ResponseEntity.ok().body(dto);
//    }
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable,
                                                            @RequestParam(name = "name", defaultValue = "") String name,
                                                            @RequestParam(value = "categoryid", defaultValue = "0") String categoryId){

        List<Long> categoryIds = Arrays.asList();
        if(!"0".equals(categoryId)){
            categoryIds = Arrays.asList(categoryId.split(",")).stream().map(x -> Long.parseLong(x)).toList();
        }
        Page<ProductDTO> dto = service.findAllPaged(pageable, name, categoryIds);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO dto, @PathVariable Long id){
        try {
            dto = service.update(dto, id);
            return ResponseEntity.ok(dto);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
