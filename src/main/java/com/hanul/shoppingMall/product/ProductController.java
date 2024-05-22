package com.hanul.shoppingMall.product;

import com.hanul.shoppingMall.review.Review;
import com.hanul.shoppingMall.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final ReviewRepository reviewRepository;

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
    public String showProductAddForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO()); // 기존 코드에서 모델에 빈 ProductDTO 추가
        return "product_add";
    }

    @GetMapping("/products/{productId}")
    public String showProductDetailForm(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.findProduct(productId);
        List<Review> reviewList = reviewRepository.findAll();

        if (productOptional.isPresent()) {
            model.addAttribute("findProduct", productOptional.get());
            model.addAttribute("reviewList", reviewList);
            return "product_detail";
        }
        return "error";
    }

    @GetMapping("/products/edit/{productId}")
    public String showProductEditForm(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.findProduct(productId);
        if (productOptional.isPresent()) {
            model.addAttribute("findProduct", productOptional.get());
            return "product_detail_edit";
        }
        return "error";
    }

    @GetMapping("/products/page/{pageNum}")
    public String getListPage(@PathVariable Integer pageNum, Model model) {
        Page<Product> productList = productRepository.findPageBy(PageRequest.of((pageNum - 1), 5));
        model.addAttribute("productList", productList);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", productList.getTotalPages());
        return "product_list";
    }

    // 이미지 presignedURL 생성
    @GetMapping("/presigned-url")
    @ResponseBody
    public String getPresignedURL(@RequestParam String filename) {
        return s3Service.generatePresignedURL(filename);
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
            BindingResult result,
            Authentication auth
    ) {
        if (result.hasErrors()) {
            log.info("productDTO Error = {}", result.getAllErrors());
            return "product_add";
        }
        productService.saveProduct(productDTO, auth);
        System.out.println("auth = " + auth);
        System.out.println("auth = " + auth.getName());
        return "redirect:/products/page/1";
    }

    // 상품 수정
    @PostMapping("/products/edit/{productId}")
    public String editProduct(
            @PathVariable Long productId,
            @Validated ProductDTO productDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            System.out.println("productDTO = " + productDTO);
            log.info("productDTO Error = {}", result.getAllErrors());
            return "redirect:/products/edit/" + productId + "?error=true";
        }

        Optional<Product> optionalProduct = productService.findProduct(productId);
        if (optionalProduct.isPresent()) {
            Product findProduct = optionalProduct.get();
            productService.updateProduct(findProduct, productDTO);
            return "redirect:/products/page/1";
        } else {
            return "redirect:/products/edit/" + productId;
        }
    }


}
