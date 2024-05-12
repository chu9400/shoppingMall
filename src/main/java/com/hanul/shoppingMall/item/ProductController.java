package com.hanul.shoppingMall.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/")
    public String landing() {
        return "index";
    }

    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "product_list";
    }

    @GetMapping("/products/new")
    public String showProductAddForm() {
        return "product_add"; // 상품 추가 페이지로 이동
    }

    @PostMapping("/products")
    public String addProduct(
            @RequestParam String title,
            @RequestParam Integer price
    )
    {
        Product product = new Product(title, price);
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/{productId}")
    String productDetail(@PathVariable Long productId, Model model) {
        Optional<Product> result = productRepository.findById(productId);
        if (result.isPresent()){
            Product findProduct = result.get();
            model.addAttribute("findProduct", findProduct);
            return "product_detail";
        } else {
            return "redirect:/products";
        }
    }
}
