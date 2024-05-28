package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;
    private final MemberService memberService;

    private Integer calculateTotalPrice(Integer price, Integer count) {
        return price * count;
    }

    public void saveSales(SalesDTO salesDTO, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Long userId = user.getId();

        Member findMember = memberService.getMember(userId);
        Integer totalPrice = calculateTotalPrice(salesDTO.getPrice(), salesDTO.getCount());

        Sales sales = new Sales(salesDTO.getTitle(), salesDTO.getPrice(), salesDTO.getCount(), findMember, totalPrice);
        salesRepository.save(sales);
    }

    public SalesListDTO getSalesList() {
        List<Sales> salesList = salesRepository.salesFindAll();

        List<SalesDTO> salesListDTOList = salesList.stream()
                .map(sales -> new SalesDTO(
                        sales.getId(),
                        sales.getTitle(),
                        sales.getPrice(),
                        sales.getCount(),
                        sales.getMember().getUsername(),
                        sales.getTotalPrice(),
                        sales.getCreated()
                ))
                .collect(Collectors.toList());

        return new SalesListDTO(salesListDTOList);

    }


}
