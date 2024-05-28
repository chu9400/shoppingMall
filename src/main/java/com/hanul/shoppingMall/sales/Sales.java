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
    private String title;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer count;

    @ManyToOne
    @JoinColumn(
        name = "member_id",
        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @Column(nullable = false)
    @CreationTimestamp
    LocalDateTime created; // 자동 생성

    @Column(nullable = false)
    private Integer totalPrice;

    public Sales(String title, Integer price, Integer count, Member member, Integer totalPrice) {
        this.title = title;
        this.price = price;
        this.count = count;
        this.member = member;
        this.totalPrice = totalPrice;
    }
}
