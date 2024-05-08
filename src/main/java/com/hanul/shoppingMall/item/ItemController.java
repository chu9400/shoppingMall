package com.hanul.shoppingMall.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @GetMapping("/")
    public String test() {
        return "index";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("name", "홍길동");
        return "list";
    }

}
