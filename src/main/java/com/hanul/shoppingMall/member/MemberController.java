package com.hanul.shoppingMall.member;

import com.hanul.shoppingMall.config.CookieUtil;
import com.hanul.shoppingMall.config.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/member/register")
    public String showRegisterForm(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/products/page/1";
        }
        return "member/register";
    }

    @GetMapping("/login")
    public String login(Authentication auth) {
//        if (auth != null && auth.isAuthenticated()) {
//            return "redirect:/products/page/1";
//        }
        System.out.println("auth = " + auth);
        if (auth == null) {
            System.out.println("auth 널인데용?");
        } else{
            System.out.println("auth = " + auth.getPrincipal());
            System.out.println("login 응답!");
        }

        return "login";
    }

    @GetMapping("/my-page/{id}")
    public String myPage(
            Model model,
            @Validated MemberMyPageDTO memberMyPageDTO
    ) {
        model.addAttribute("member", memberMyPageDTO);
        return "member/mypage";
    }

    @PostMapping("/member")
    public String registerMember(@Validated MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);
        return "index";
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);

        Cookie cookie = new Cookie("jwtToken", jwt);
        cookie.setMaxAge(2000); // 쿠키 수명 대략 30분
        cookie.setHttpOnly(true); // 쿠키 보안
        cookie.setPath("/"); // 모든 경로에 쿠키 체크
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    public String mypageJWT(HttpServletRequest request) {
        Cookie cookie = CookieUtil.getCookie(request, "jwtToken");
        if (cookie != null) {
            String token = cookie.getValue();
            Claims claims = JwtUtil.extractToken(token);
            return "JWT 검증 성공: " + claims.get("username");
        }
        return "JWT 검증 실패";
    }

    @GetMapping("/my-page")
    public String myPage(Model model) {
        return "member/mypage2";
    }

}
