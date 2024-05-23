package com.hanul.shoppingMall.review;

import com.hanul.shoppingMall.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
//    Page<Product> findPageBy(Pageable pageable);
    List<Review> findAllByParentId(Long parentId);
}
