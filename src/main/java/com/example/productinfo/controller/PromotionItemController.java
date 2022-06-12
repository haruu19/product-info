package com.example.productinfo.controller;

import com.example.productinfo.entity.PromotionItem;
import com.example.productinfo.repo.PromotionItemRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"4. Promotion-Item"})
@RequiredArgsConstructor
@RestController
public class PromotionItemController {

    private final PromotionItemRepo promotionItemRepo;

    @ApiOperation(value = "프로모션-아이템 관계 입력", notes = "프로모션-아이템 관계를 입력한다.")
    @PostMapping(value = "/promotion-item")
    public PromotionItem save(@RequestBody PromotionItem promotionItem) {
        return promotionItemRepo.save(promotionItem);
    }

    @ApiOperation(value = "프로모션-아이템 관계 삭제", notes = "프로모션-아이템 관계를 삭제한다.")
    @DeleteMapping(value = "/promotion-item/{promotionItemId}")
    public void delete(@ApiParam(value = "프로모션-아이템 아이디", required = true) @RequestHeader(value="promotion-item-id") Long promotionItemId) {
        promotionItemRepo.deleteById(promotionItemId);
    }
}