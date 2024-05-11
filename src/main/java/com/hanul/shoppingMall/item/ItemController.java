package com.hanul.shoppingMall.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/")
    public String landing() {
        return "index";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> itemList = itemRepository.findAll();
        model.addAttribute("itemList", itemList);
        return "list";
    }

    @GetMapping("/items/new")
    public String showItemAddForm() {
        return "writeItem"; // 상품 추가 페이지로 이동
    }

    @PostMapping("/items")
    public String addItem(
            @RequestParam String title,
            @RequestParam Integer price
        )
    {
        Item item = new Item(title, price);
        itemRepository.save(item);

        return "redirect:/list";
    }



}
