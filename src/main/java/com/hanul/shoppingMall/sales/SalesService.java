package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.*;
import com.hanul.shoppingMall.product.Product;
import com.hanul.shoppingMall.product.ProductRepository;
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
    private final ProductRepository productRepository;
    private final MemberService memberService;

    private Integer calculateTotalPrice(Integer price, Integer count) {
        return price * count;
    }

    public void saveSales(Long productId, SalesDTO salesDTO, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Long userId = user.getId();

        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return;
        }
        Product findProduct = productOptional.get();
        Member findMember = memberService.getMember(userId);
        Integer totalPrice = calculateTotalPrice(salesDTO.getProductPrice(), salesDTO.getCount());

        Sales sales = new Sales(salesDTO.getCount(), totalPrice, findProduct, findMember);
        salesRepository.save(sales);
    }


    public SalesListDTO getSalesList() {
        List<Sales> salesList = salesRepository.salesFindAll();
        // 상품 정보도 같이 가져와야하는 코드 짜기.

        List<SalesDTO> salesListDTOList = salesList.stream()
                .map(sales -> new SalesDTO(
                        sales.getId(),
                        sales.getProduct().getTitle(),
                        sales.getProduct().getPrice(),
                        sales.getCount(),
                        sales.getMember().getUsername(),
                        sales.getTotalPrice()
                ))
                .collect(Collectors.toList());

        return new SalesListDTO(salesListDTOList);

    }


}
