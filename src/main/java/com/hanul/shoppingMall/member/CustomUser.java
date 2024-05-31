package com.hanul.shoppingMall.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter @Setter @ToString
public class CustomUser extends User {

    // 기존의 MyUserDetailsService 변수 추가
    private Long id;
    private String displayName;

    public CustomUser (String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
