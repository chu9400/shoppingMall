package com.hanul.shoppingMall.sales;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class SalesDTO {

    @NotNull(message = "title 는 공백일 수 없습니다.")
    private String title;

    @NotNull(message = "price 는 공백일 수 없습니다.")
    private Integer price;

    @NotNull(message = "count 는 공백일 수 없습니다.")
    private Integer count;

    @NotNull(message = "username 는 공백일 수 없습니다.")
    private String username;

    public SalesDTO(String title, Integer price, Integer count, String username) {
        this.title = title;
        this.price = price;
        this.count = count;
        this.username = username;
    }
}
