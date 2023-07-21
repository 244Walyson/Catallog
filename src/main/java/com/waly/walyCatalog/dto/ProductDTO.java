package com.waly.walyCatalog.dto;

import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductDTO extends RepresentationModel<ProductDTO> {
    private Long id;
    @NotBlank(message = "campo obrigatório")
    private String name;
    @NotBlank(message = "campo obrigatório")
    private String description;
    @Positive(message = "Preço deve ser um valor positivo")
    private Double price;
    private String imgUrl;
    @PastOrPresent(message = "a data não pode ser data futura")
    private Instant date;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(){}

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
    }

    public ProductDTO(Product entity, Set<Category> categories){
        this(entity);
        categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    @PrePersist
    private void setDate(){
        date = Instant.now();
    }
}
