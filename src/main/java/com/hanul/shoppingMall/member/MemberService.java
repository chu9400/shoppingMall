package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerMember(MemberDTO memberDTO) {
        String encodePassword = bCryptPasswordEncoder.encode(memberDTO.getPassword());
        Member member = new Member(memberDTO.getUsername(), encodePassword, memberDTO.getDisplayName());
        memberRepository.save(member);
    }



}
