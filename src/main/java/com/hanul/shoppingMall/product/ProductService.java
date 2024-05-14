package com.hanul.shoppingMall.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

    public Product saveProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.getTitle(), productDTO.getPrice());
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

}
