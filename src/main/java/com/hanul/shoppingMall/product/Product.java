package com.hanul.shoppingMall.product;

import com.hanul.shoppingMall.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter @Setter
@NoArgsConstructor
@Slf4j
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String productImgUrl;

    @Column(nullable = false)
    private String username;


    public Product(String title, Integer price, String productImgUrl, String username) {
        this.title = title;
        this.price = price;
        this.productImgUrl = productImgUrl;
        this.username = username;
    }
}
