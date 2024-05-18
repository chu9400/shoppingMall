package com.hanul.shoppingMall.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        Member findUser = memberOptional.get();

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        if (findUser.getUsername().equals("admin") || findUser.getUsername().equals("kim")) {
            authorities.add(new SimpleGrantedAuthority("관리자"));
        } else {
            authorities.add(new SimpleGrantedAuthority("일반유저"));
        }
        CustomUser customUser = new CustomUser(findUser.getUsername(), findUser.getPassword(), authorities);
        customUser.setId(findUser.getId());
        customUser.setDisplayName(findUser.getDisplayName());

        return customUser;
    }
}
