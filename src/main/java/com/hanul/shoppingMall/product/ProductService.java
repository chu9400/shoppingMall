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

    public Product editProduct(Product product, ProductDTO productDTO) {
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        return productRepository.save(product);
    }

    public Optional<Product> findProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public String renderProductForm(Long productId, Model model, String viewName) {
        Optional<Product> productOptional = findProduct(productId);
        if (productOptional.isPresent()) {
            model.addAttribute("findProduct", productOptional.get());
            return viewName;
        } else {
            return "redirect:/products";
        }
    }

    public ResponseEntity<String> deleteProductStringResponse(Long productId) {
        Optional<Product> productOptional = findProduct(productId);
        if (productOptional.isPresent()) {
            Product findProduct = productOptional.get();
            productRepository.deleteById(findProduct.getId());
            log.info("삭제 완료");
            log.info("삭제 상품 id : {}", findProduct.getId());
            log.info("삭제 상품명 : {}", findProduct.getTitle());
            return ResponseEntity.status(200).body("삭제완료!");
        } else {
            log.info("삭제 오류");
            log.info("삭제 오류 상품 id : {}", productId);
            return ResponseEntity.status(400).body("삭제오류!");
        }

    }


}
