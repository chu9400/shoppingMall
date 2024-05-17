package com.hanul.shoppingMall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화(배포시 제거)
        http.csrf((csrf) -> csrf.disable());

        // 모든 URL에 대해 인증 없이 접근 허용
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );

        // 로그인 폼 설정
        http.formLogin((formLogin) -> formLogin.loginPage("/login") // 로그인 폼 경로
                .defaultSuccessUrl("/") // 로그인 성공 후 이동 경로
        );

        //로그아웃 주소 설정
        http.logout(logout -> logout.logoutUrl("/logout"));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

