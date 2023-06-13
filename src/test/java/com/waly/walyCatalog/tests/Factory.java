package com.waly.walyCatalog.tests;

import com.waly.walyCatalog.dto.ProductDTO;
import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L, "phone", "Good phone", 800.00, "https://image.com");
        product.getCategories().add(new Category(1L, "Eletronics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory(){
        return new Category(1L, "eletronics");
    }
}
