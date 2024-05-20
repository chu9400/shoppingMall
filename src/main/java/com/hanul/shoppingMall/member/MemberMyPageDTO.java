package com.hanul.shoppingMall.member;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberMyPageDTO {
    private String username;
    private String displayName;

    public MemberMyPageDTO(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
}
