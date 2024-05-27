package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.Member;
import com.hanul.shoppingMall.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;

    public void saveSales(SalesDTO salesDTO) {

        Optional<Member> optionalMember = memberRepository.findByUsername(salesDTO.getUsername());

        if (optionalMember.isPresent()) {
            Long findMemberId = optionalMember.get().getId();
            Sales sales = new Sales(salesDTO.getTitle(), salesDTO.getPrice(), salesDTO.getPrice(), findMemberId );
            salesRepository.save(sales);
        }

    }
}
