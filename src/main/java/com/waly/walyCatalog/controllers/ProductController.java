package com.waly.walyCatalog.controllers;

import com.waly.walyCatalog.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {


    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        List<ProductDTO> dto = new ArrayList<>();
        dto.add(new ProductDTO(1L,"carro","email",230.00,"carro.com"));
        dto.add(new ProductDTO(2L,"carro","email",230.00,"carro.com"));
        return ResponseEntity.ok(dto);
    }
}
