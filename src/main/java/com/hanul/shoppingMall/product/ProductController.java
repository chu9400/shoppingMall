package com.hanul.shoppingMall.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/")
    public String showlanding() {
        return "index";
    }

    @GetMapping("/products")
    public String showProductList(Model model) {
        productService.findAllProduct(model);
        return "product_list";
    }

    @GetMapping("/products/new")
    public String showProductAddForm() {
        return "product_add";
    }

    @GetMapping("/products/{productId}")
    public String showProductDetailForm(@PathVariable Long productId, Model model) {
        return productService.renderProductForm(productId, model, "product_detail");
    }

    @GetMapping("/products/edit/{productId}")
    public String showProductEditForm(@PathVariable Long productId, Model model) {
        return productService.renderProductForm(productId, model, "product_detail_edit");
    }

    // 상품 삭제
    @DeleteMapping("/products/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductStringResponse(productId);
    }

    // 상품 등록
    @PostMapping("/products")
    public String addProduct(
            @Validated ProductDTO productDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.info("productDTO Error = {}", result.getAllErrors());
            return "product_add";
        }
        productService.saveProduct(productDTO);
        return "redirect:/products";
    }

    // 상품 수정
    @PostMapping("/products/edit/{productId}")
    public String editProduct(
            @PathVariable Long productId,
            @Validated ProductDTO productDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            log.info("productDTO Error = {}", result.getAllErrors());
            return "redirect:/products/edit/" + productId + "?error=true";
        }

        Optional<Product> optionalProduct = productService.findProduct(productId);
        if (optionalProduct.isPresent()) {
            Product findProduct = optionalProduct.get();
            productService.editProduct(findProduct, productDTO);
            return "redirect:/products";
        } else {
            return "redirect:/products/edit/" + productId;
        }
    }

}
