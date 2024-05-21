package com.hanul.shoppingMall.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
public class ProductDTO {
    @NotNull(message = "Title cannot be null")
    @Size(max = 50, message = "Title must be less than or equal to 50 characters")
    private String title;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Integer price;

    @NotNull(message = "이미지 URL이 있어야합니다.")
    private String productImgUrl;

}
