package com.waly.walyCatalog.Repositories;

import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.projections.ProductsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

//    @Query("SELECT p.name AS name, p.id AS id " +
//            "FROM Product p " +
//            "INNER JOIN p.categories c " +
//            "WHERE (:categoryIds IS NULL OR c.id IN :categoryIds) AND UPPER(p.name) " +
//            "LIKE UPPER(CONCAT('%', :name, '%'))")
//    Page<ProductsProjection> searchProducts(Pageable pageable, String name, List<Long> categoryIds);
}

