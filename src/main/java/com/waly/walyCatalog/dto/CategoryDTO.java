package com.waly.walyCatalog.dto;

import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(){}

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
