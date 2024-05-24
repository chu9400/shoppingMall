package com.hanul.shoppingMall.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findPageBy(Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE MATCH(title) AGAINST(?1)", nativeQuery = true)
    Page<Product> fullTextSearchProduct(String text, Pageable pageable);
}
