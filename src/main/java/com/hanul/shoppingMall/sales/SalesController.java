package com.hanul.shoppingMall.sales;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @GetMapping("/sales")
    public String showSale() {
        return "sale-confirmation";
    }

    @PostMapping("/sales")
    public String addSales(
        @Validated SalesDTO salesDTO,
        BindingResult bindingResult,
        @RequestParam Long productId
    ) {
        if (bindingResult.hasErrors()) {
            log.info("SalesDTO 에러 발생 = {} ", bindingResult.getAllErrors());
            return "error";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return "redirect:/products/" + productId + "?usernameError=true";
        }

        salesService.saveSales(salesDTO);
        return "redirect:/sales";
    }
}
