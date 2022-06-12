package com.example.productinfo.controller;

import com.example.productinfo.entity.Item;
import com.example.productinfo.repo.ItemRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.productinfo.service.ItemService;

import java.util.List;

@Api(tags = {"2. Item"})
@RequiredArgsConstructor
@RestController
public class ItemController {

    private final ItemRepo itemRepo;
    private final ItemService itemService;

    @ApiOperation(value = "상품 조회", notes = "사용자가 구매할 수 있는 상품 정보를 조회한다.")
    @GetMapping(value = "/item/{userId}")
    public List<Item> findByUserId(@ApiParam(value = "사용자 아이디", required = true) @RequestHeader(value="user-id") Long userId) {
        return itemService.findByUserId(userId);
    }

    @ApiOperation(value = "상품 입력", notes = "상품 정보를 입력한다.")
    @PostMapping(value = "/item")
    public Item save(@RequestBody Item item) {
        return itemRepo.save(item);
    }

    @ApiOperation(value = "상품 삭제", notes = "상품 정보를 삭제한다.")
    @DeleteMapping(value = "/item/{itemId}")
    public void delete(@ApiParam(value = "상품 아이디", required = true) @RequestHeader(value="item-id") Long itemId) {
        itemRepo.deleteById(itemId);
    }
}