package com.waly.walyCatalog.services;

import com.waly.walyCatalog.Repositories.CategoryRepository;
import com.waly.walyCatalog.dto.CategoryDTO;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.services.Exceptions.DatabaseException;
import com.waly.walyCatalog.services.Exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;


    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable){
        Page<Category> result = repository.findAll(pageable);
        return result.map(x -> new CategoryDTO(x));
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

    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id) {
        try{
            Category cat = repository.getReferenceById(id);
            cat.setName(dto.getName());
            repository.save(cat);
            return new CategoryDTO(cat);
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
}
