package com.hanul.shoppingMall.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@NoArgsConstructor
public class ReviewDTO {

    @NotNull(message = "username 는 공백일 수 없습니다.")
    private String username;

    @NotNull(message = "parentId 는 공백일 수 없습니다.")
    private Long parentId;

    @NotNull(message = "content 는 공백일 수 없습니다.")
    private String content;

    public ReviewDTO(String username, Long parentId, String content) {
        this.username = username;
        this.parentId = parentId;
        this.content = content;
    }
}
