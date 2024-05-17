package com.hanul.shoppingMall.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProductDTO {
    @NotNull(message = "Title cannot be null")
    @Size(max = 50, message = "Title must be less than or equal to 50 characters")
    private String title;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private Integer price;

}
