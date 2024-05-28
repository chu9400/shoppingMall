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

    @NotNull(message = "title 는 공백일 수 없습니다.")
    private String title;

    @NotNull(message = "price 는 공백일 수 없습니다.")
    private Integer price;

    @NotNull(message = "count 는 공백일 수 없습니다.")
    private Integer count;

    private String username;
    private Integer totalPrice;
    private LocalDateTime created;

    public SalesDTO(String title, Integer price, Integer count) {
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public SalesDTO(Long id, String title, Integer price, Integer count, String username, Integer totalPrice, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.count = count;
        this.username = username;
        this.totalPrice = totalPrice;
        this.created = created;
    }
}
