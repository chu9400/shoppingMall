package com.hanul.shoppingMall.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void saveReview(ReviewDTO reviewDTO) {

        boolean exists = reviewRepository.existsByUsernameAndParentId(reviewDTO.getUsername(), reviewDTO.getParentId());
        if (exists) {
            throw new IllegalArgumentException("이미 리뷰를 작성하셨습니다.");
        }

        Review review = new Review(reviewDTO.getUsername(), reviewDTO.getContent(), reviewDTO.getParentId());
        reviewRepository.save(review);
    }


}
