package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveMember(MemberDTO memberDTO) {

        String encodePassword = bCryptPasswordEncoder.encode(memberDTO.getPassword());
        Member member = new Member(memberDTO.getUsername(), encodePassword, memberDTO.getDisplayName());
        memberRepository.save(member);
    }

}
