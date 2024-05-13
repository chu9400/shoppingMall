package com.hanul.shoppingMall.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("/")
    public String landing() {
        return "index";
    }

    @GetMapping("/products")
    public String productList(Model model) {
        productService.findAllProduct(model);
        return "product_list";
    }

    @GetMapping("/products/new")
    public String showProductAddForm() {
        return "product_add";
    }

    @PostMapping("/products")
    public String addProduct(
            @Validated ProductDTO productDTO,
            BindingResult result,
            Model model
        )
    {
        boolean checkForm = productService.checkAddProductForm(result, productDTO);

        if (checkForm) {
            productService.saveProduct(productDTO);
            return "redirect:/products";
        }  else {
            productService.errorAddProductForm(result, model);
            return "product_add";
        }
    }


    @GetMapping("/products/{productId}")
    String productDetail(@PathVariable Long productId, Model model) {
        boolean result_product = productService.findProduct(productId, model);

        if (result_product) {
            return "product_detail";
        } else {
            return "redirect:/products";
        }
    }

}
