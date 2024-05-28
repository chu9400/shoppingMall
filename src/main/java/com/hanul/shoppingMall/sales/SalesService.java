package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final MemberService memberService;

    public void saveSales(SalesDTO salesDTO, Authentication auth) {

        CustomUser user = (CustomUser) auth.getPrincipal();
        Long userId = user.getId();

        Member findMember = memberService.findMemberForId(userId);
        Integer totalPrice = calculateTotalPrice(salesDTO.getPrice(), salesDTO.getCount());

        Sales sales = new Sales(salesDTO.getTitle(), salesDTO.getPrice(), salesDTO.getCount(), findMember, totalPrice);
        salesRepository.save(sales);

    }


    private Integer calculateTotalPrice(Integer price, Integer count) {
        return price * count;
    }

}
