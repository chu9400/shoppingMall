package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if (memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("로그인 아이디를 찾을 수 없습니다.");
        }

        Member member = memberOptional.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(member.getUsername()) || "kim".equals(member.getUsername())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        CustomUser customUser = new CustomUser(member.getUsername(), member.getPassword(), authorities);
        customUser.setId(member.getId());
        customUser.setDisplayName(member.getDisplayName());

        return customUser;
    }
}