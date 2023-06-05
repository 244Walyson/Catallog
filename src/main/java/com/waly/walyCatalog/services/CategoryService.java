package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.dto.CategoryDTO;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> result = repository.findAll();
        return result.stream().map(x -> new CategoryDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
      Category dto = repository.findById(id).orElseThrow(() -> new NotFoundException("category not found"));
        return new CategoryDTO(dto);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category cat = new Category();
        cat.setName(dto.getName());
        repository.save(cat);
        return new CategoryDTO(cat);
    }
}
