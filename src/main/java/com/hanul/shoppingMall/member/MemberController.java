package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String showRegisterForm() {
        return "member/register";
    }

    @PostMapping("/member")
    public String registerMember (@Validated MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);
        return "redirect:/products";
    }
}
