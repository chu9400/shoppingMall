package com.hanul.shoppingMall.product;

import com.hanul.shoppingMall.review.Review;
import com.hanul.shoppingMall.review.ReviewDTO;
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

    @GetMapping("/products/new")
    public String showProductAddForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO()); // 기존 코드에서 모델에 빈 ProductDTO 추가
        return "product_add";
    }

    // 상품 상세 페이지
    @GetMapping("/products/{productId}")
    public String showProductDetailForm(@PathVariable Long productId, Model model) {

        //상품 조회
        Optional<Product> productOptional = productService.findProduct(productId);

        //리뷰 조회
        List<Review> reviewListById = reviewRepository.findAllByParentId(productId);

        if (productOptional.isPresent()) {
            model.addAttribute("findProduct", productOptional.get());
            model.addAttribute("reviewList", reviewListById);
            return "product_detail";
        }
        return "error";
    }

    // 상품 조회 페이지
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
            Authentication auth,
            Model model
    ) {
        if (result.hasErrors()) {
            log.info("productDTO Error = {}", result.getAllErrors());
            model.addAttribute("productDTO", productDTO); // 폼 재렌더링 시 입력값 유지
            model.addAttribute("errorMessage", "상품과 가격을 다시 적어주세요."); // 에러 메시지 설정
            return "product_add";
        }
        productService.saveProduct(productDTO, auth);
        return "redirect:/products/page/1";
    }

    // 상품 수정 : GET
    @GetMapping("/products/edit/{productId}")
    public String showProductEditForm(@PathVariable Long productId, Model model) {
        Optional<Product> productOptional = productService.findProduct(productId);
        if (productOptional.isPresent()) {
            ProductDTO productDTO = productService.convertToDto(productOptional.get());
            model.addAttribute("productDTO", productDTO);
            model.addAttribute("productId", productId);
            return "product_detail_edit";
        }
        return "error";
    }

    // 상품 수정 : POST
    @PostMapping("/products/edit/{productId}")
    public String editProduct(
            @PathVariable Long productId,
            @Validated ProductDTO productDTO,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            log.info("productDTO Error = {}", result.getAllErrors());
            model.addAttribute("productDTO", productDTO); // 폼 재렌더링 시 입력값 유지
            model.addAttribute("errorMessage", "상품과 가격을 다시 적어주세요."); // 에러 메시지 설정
            model.addAttribute("productId", productId); //
            return "product_detail_edit";
        }

        Optional<Product> optionalProduct = productService.findProduct(productId);
        if (optionalProduct.isPresent()) {
            productService.updateProduct(optionalProduct.get(), productDTO);
            return "redirect:/products/page/1";
        } else {
            model.addAttribute("errorMessage", "해당 상품을 찾을 수 없습니다.");
            model.addAttribute("productDTO", productDTO); // 폼 재렌더링 시 입력값 유지
            model.addAttribute("productId", productId);
            return "product_detail_edit";
        }
    }


    // 상품 검색 페이지 이제 여기에 페이지네이션 기능 추가해야함 한 페이지에 몇 개 보이게 할까나
    @GetMapping("/products/searchProduct/page/{pageNum}")
    public String searchProduct(
            @PathVariable Integer pageNum,
            @RequestParam String searchText,
            Model model
        ) {
        
        // 검사 로직
        if (searchText == null || searchText.trim().isEmpty()) {
            return "redirect:/products/page/1?nullError";
        }
        if (searchText.length() < 2) {
            return "redirect:/products/page/1?searchError";
        }

        // 정상 로직
        // FullTextIndex & Pagination
        Page<Product> productsAndPage = productService.getProductsAndPage(pageNum, searchText);

        model.addAttribute("productList", productsAndPage.getContent());
        model.addAttribute("searchText", searchText);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", productsAndPage.getTotalPages());

        return "product_list_search";
    }


}
