package com.waly.walyCatalog.Repositories;

import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
