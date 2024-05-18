package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/register")
    public String showRegisterForm(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/products";
        }
        return "member/register";
    }

    @GetMapping("/login")
    public String login(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/products";
        }
        return "login";
    }


    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        // 임시 코드
        System.out.println(auth.getName()); //아이디출력가능
        System.out.println(auth.isAuthenticated()); //로그인여부 검사가능
        return "member/mypage";
    }

    @PostMapping("/member")
    public String registerMember (@Validated MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);
        return "redirect:/products";
    }
}
