package com.hanul.shoppingMall.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"username", "parentId"}) }) // 이 테이블에 username과 parentId라는 두 컬럼의 조합이 중복되지 않도록 하는 규칙이다.
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long parentId;

    public Review(String username, String content, Long parentId) {
        this.username = username;
        this.content = content;
        this.parentId = parentId;
    }
}
