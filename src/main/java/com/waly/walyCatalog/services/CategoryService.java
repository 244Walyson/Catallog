package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.dto.CategoryDTO;
import com.waly.walyCatalog.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll(){
        List<Category> dto = categoryRepository.findAll();
        return dto.stream().map(x-> new CategoryDTO(x)).toList();
    }
}
