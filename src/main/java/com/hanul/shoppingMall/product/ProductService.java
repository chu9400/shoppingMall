package com.hanul.shoppingMall.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void findAllProduct(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
    }

    public Product saveProduct(ProductDTO productDTO, Authentication auth) {
        Product product = new Product(productDTO.getTitle(), productDTO.getPrice(), productDTO.getProductImgUrl(), auth.getName());
        return productRepository.save(product);
    }

    public Optional<Product> findProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public void updateProduct(Product findProduct, ProductDTO productDTO) {
        findProduct.setTitle(productDTO.getTitle());
        findProduct.setPrice(productDTO.getPrice());
        findProduct.setProductImgUrl(productDTO.getProductImgUrl());
        productRepository.save(findProduct);
    }

    public ProductDTO convertToDto(Product product) {
        return new ProductDTO(
                product.getTitle(),
                product.getPrice(),
                product.getProductImgUrl(),
                product.getUsername()
        );
    }

    public ResponseEntity<String> deleteProductStringResponse(Long productId) {
        Optional<Product> productOptional = findProduct(productId);
        if (productOptional.isPresent()) {
            Product findProduct = productOptional.get();
            productRepository.deleteById(findProduct.getId());
            log.info("삭제 완료: 상품 ID = {}, 상품명 = {}", productId, productOptional.get().getTitle());
            return ResponseEntity.ok("삭제 완료!");

        } else {
            log.error("삭제 오류: 상품 ID = {}", productId);
            return ResponseEntity.status(400).body("삭제 오류!");
        }

    }


}
