package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.CustomUser;
import com.hanul.shoppingMall.member.Member;
import com.hanul.shoppingMall.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;
    private final MemberRepository memberRepository;

    @GetMapping("/sales")
    public String showSale() {
        return "sales/sales-confirmation";
    }

    // 주문 요청
    @PostMapping("/sales")
    public String addSales(
        @Validated SalesDTO salesDTO,
        BindingResult bindingResult,
        Long productId,
        Authentication auth
    ) {
        if (auth == null) {
            log.info("====== 로그인 에러 발생! auth = {} ======", auth);
            return "redirect:/products/" + productId + "?usernameError=true";
        }

        if (bindingResult.hasErrors()) {
            log.info("====== SalesDTO 에러 발생 = {} ======", bindingResult.getAllErrors());
            return "error";
        }

        salesService.saveSales(productId, salesDTO, auth);
        return "redirect:/sales";
    }

    // 주문 목록 조회 페이지
    @GetMapping("/sales/all")
    public String getSaleAll(Authentication auth, Model model) {
        // 관리자만 보이게
        if (auth == null ) {
            log.info("====== 주문 내역 페이지 접근 에러 발생! auth = {} ======", auth);
            return "redirect:/";
        }

        CustomUser user = (CustomUser) auth.getPrincipal();
        String username = user.getUsername();
        boolean isAdmin = username.equals("kim") || username.equals("admin");

        if (!isAdmin) {
            log.info("====== 주문 내역 페이지 접근 권한 에러 발생! auth = {} ======", auth);
            return "redirect:/";
        }

        SalesListDTO salesList = salesService.getSalesList();
        model.addAttribute("productList", salesList.getSalesDTOList());
        
        // <p><span>주문 날짜 : </span>  <span th:text="${product.created}"></span></p> 
        // 이거 가져오기 확인
        return "sales/sales_list";

    }

}


