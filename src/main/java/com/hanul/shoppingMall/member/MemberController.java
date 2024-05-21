package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/member/register")
    public String showRegisterForm(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/products/page/1";
        }
        return "member/register";
    }

    @GetMapping("/login")
    public String login(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/products/page/1";
        }
        return "login";
    }


    @GetMapping("/my-page/{id}")
    public String myPage(@PathVariable Long id, Model model) {
        MemberMyPageDTO memberMyPageDTO = memberService.getMember(id);
        model.addAttribute("member", memberMyPageDTO);
        return "member/mypage";
    }

    @PostMapping("/member")
    public String registerMember(@Validated MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);
        return "index";
    }


}
