package com.hanul.shoppingMall.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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

    // 상품 입력값 유효성 검사
    public boolean checkAddProductForm(BindingResult result, ProductDTO productDTO) {
        if (result.hasErrors()) {
            log.error("productDTO = {}", productDTO);
            log.error("productDTO Error = {}", result.getAllErrors());
            return false;
        } else {
            return true;
        }
    }

    // 상품 입력값 유효성 실패시 실행
    public void errorAddProductForm(BindingResult result, Model model) {
        List<ObjectError> errors = result.getAllErrors();
        model.addAttribute("errors", errors);
    }

    // 상품 입력값 유효성 성공시 실행
    public void saveProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.getTitle(), productDTO.getPrice());
        productRepository.save(product);
    }

    public boolean findProduct(@PathVariable Long productId, Model model) {
        Optional<Product> result = productRepository.findById(productId);
        if (result.isPresent()){
            Product findProduct = result.get();
            model.addAttribute("findProduct", findProduct);
            return true;
        } else {
            return false;
        }
    }

}
