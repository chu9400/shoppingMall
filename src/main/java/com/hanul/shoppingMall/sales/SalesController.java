package com.hanul.shoppingMall.sales;

import com.hanul.shoppingMall.member.CustomUser;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;
    private final SalesRepository salesRepository;


    @GetMapping("/sales")
    public String showSale() {
        return "sales/sales-confirmation";
    }

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

        salesService.saveSales(salesDTO, auth);
        return "redirect:/sales";
    }

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
        return "sales/sales_list";
    }

}


