package com.hanul.shoppingMall.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String title;
    private Integer price;

    public Item(String title, Integer price) {
        this.title = title;
        this.price = price;
    }
    


}
