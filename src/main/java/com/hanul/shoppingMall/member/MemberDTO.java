package com.hanul.shoppingMall.member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDTO {

    @NotNull(message = "username cannot be null")
    @Size(min = 3, message = "username은 3글자 이상이어야 합니다.")
    private String username;

    @NotNull(message = "password cannot be null")
    @Size(min = 4, message = "password는 4글자 이상이어야 합니다.")
    private String password;

    @NotNull(message = "displayName을 입력해 주세요.")
    private String displayName;
}
