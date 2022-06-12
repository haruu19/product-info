package com.example.productinfo.controller;

import com.example.productinfo.entity.Promotion;
import com.example.productinfo.repo.PromotionRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. Promotion"})
@RequiredArgsConstructor
@RestController
public class PromotionController {

    private final PromotionRepo promotionRepo;

    @ApiOperation(value = "프로모션 조회", notes = "프로모션 정보를 조회한다.")
    @GetMapping(value = "/promotion")
    public List<Promotion> findAllItem() {
        return promotionRepo.findAll();
    }

    @ApiOperation(value = "프로모션 입력", notes = "프로모션 정보를 입력한다.")
    @PostMapping(value = "/promotion")
    public Promotion save(@RequestBody Promotion promotion) {
        return promotionRepo.save(promotion);
    }

    @ApiOperation(value = "프로모션 삭제", notes = "프로모션 정보를 삭제한다.")
    @DeleteMapping(value = "/promotion/{promotionId}")
    public void delete(@ApiParam(value = "프로모션 아이디", required = true) @RequestHeader(value="promotion-id") Long promotionId) {
        promotionRepo.deleteById(promotionId);
    }
}