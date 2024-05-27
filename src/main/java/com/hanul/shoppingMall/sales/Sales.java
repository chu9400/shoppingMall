package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.Member;
import com.hanul.shoppingMall.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private Integer price;
    private Integer count;
    private Long memberId;

    @CreationTimestamp
    LocalDateTime created;

    public Sales(String title, Integer price, Integer count, Long memberId) {
        this.title = title;
        this.price = price;
        this.count = count;
        this.memberId = memberId;
    }
}
