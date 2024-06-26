package com.hanul.shoppingMall.member;

import com.hanul.shoppingMall.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Member getMember(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new MemberNotFoundException("요청한 Member id : " + id + " - 찾을 수 없음!");
        }
        return optionalMember.get();
    }
}
