package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
public class SalesDTO {

    private Long id;

    @NotNull(message = "productTitle 는 공백일 수 없습니다.")
    private String productTitle;

    @NotNull(message = "productPrice 는 공백일 수 없습니다.")
    private Integer productPrice;

    @NotNull(message = "count 는 공백일 수 없습니다.")
    private Integer count;

    private String username;

    private Integer totalPrice;

    public SalesDTO(String productTitle, Integer productPrice, Integer count) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.count = count;
    }

    public SalesDTO(Long id, String productTitle, Integer productPrice, Integer count, String username, Integer totalPrice) {
        this.id = id;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.count = count;
        this.username = username;
        this.totalPrice = totalPrice;
    }
}
