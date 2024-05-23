package com.hanul.shoppingMall.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    public String addReview(
            @Validated ReviewDTO reviewDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            log.info("ReviewDTO 에러 발생 = {} ", bindingResult.getAllErrors());
            return "redirect:/products/" + reviewDTO.getParentId(); // 리뷰 작성에 실패하면 해당 상품 페이지로 리다이렉트
        }

        try {
            reviewService.saveReview(reviewDTO);
        } catch (IllegalArgumentException e) {
            log.info("리뷰 작성 실패 : {}", e.getMessage());
            return "redirect:/products/" + reviewDTO.getParentId() + "?error=" + e.getMessage(); // 에러 메시지와 함께 리다이렉트
        }

        return "redirect:/products/" + reviewDTO.getParentId(); // 성공 시 해당 상품 페이지로 리다이렉트

    }

}
