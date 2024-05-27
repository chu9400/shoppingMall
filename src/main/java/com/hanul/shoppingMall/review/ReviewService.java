package com.hanul.shoppingMall.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public boolean checkReviewExists(ReviewDTO reviewDTO) {
        return reviewRepository.existsByUsernameAndParentId(reviewDTO.getUsername(), reviewDTO.getParentId());
    }

    public void saveReview(ReviewDTO reviewDTO) {
        Review review = new Review(reviewDTO.getUsername(), reviewDTO.getContent(), reviewDTO.getParentId());
        reviewRepository.save(review);
    }


}
