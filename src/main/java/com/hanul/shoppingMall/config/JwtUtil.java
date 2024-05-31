package com.hanul.shoppingMall.config;
import com.hanul.shoppingMall.member.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("U2VjbXJlS2V5RXhhbXBzZUFCR0hJSlqLTU5PUFFSU1RVVldYWVo="));

    public static String createToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        List<String> authorities = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("displayName", user.getDisplayName())
                .claim("memberId", user.getId())
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2000000))
                .signWith(key)
                .compact();
    }

    public static Claims extractToken(String token) {
        return Jwts.parser()
                .setSigningKey(key) // 여기서 key는 서명 키입니다. 이 키는 JWT를 생성할 때 사용된 키와 일치해야 합니다.
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

