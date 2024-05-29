package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.Member;
import com.hanul.shoppingMall.product.Product;
import jakarta.persistence.*;
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

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime created; // 자동 생성

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "member_id",
        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    public Sales(Integer count, Integer totalPrice, Product product, Member member) {
        this.count = count;
        this.totalPrice = totalPrice;
        this.product = product;
        this.member = member;
    }
}
