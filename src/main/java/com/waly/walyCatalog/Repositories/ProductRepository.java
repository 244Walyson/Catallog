package com.waly.walyCatalog.Repositories;

import com.waly.walyCatalog.entities.Category;
import com.waly.walyCatalog.entities.Product;
import com.waly.walyCatalog.projections.ProductsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true, value = """
            SELECT DISTINCT tb_product.id, tb_product.name
            FROM tb_product
            INNER JOIN tb_product_category
            ON tb_product.id=tb_product_category.product_id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))
            AND UPPER(tb_product.name) LIKE(UPPER(CONCAT('%',:name,'%')))
            ORDER BY tb_product.name""",
            countQuery = """
            SELECT COUNT(*) FROM(
            SELECT DISTINCT tb_product.id, tb_product.name
            FROM tb_product
            INNER JOIN tb_product_category
            ON tb_product.id=tb_product_category.product_id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))
            AND UPPER(tb_product.name) LIKE(UPPER(CONCAT('%',:name,'%')))
            ORDER BY tb_product.name
            )
             AS tb_result
            """)
    Page<ProductsProjection> searchProducts(Pageable pageable, String name, List<Long> categoryIds);

    @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj.id IN :productIds ORDER BY obj.name")
    List<Product> searchProductsWithCategories(List<Long> productIds);
}

