package com.hanul.shoppingMall.member;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "password cannot be null")
    private String password;

    @NotNull(message = "displayName cannot be null")
    private String displayName;
}
