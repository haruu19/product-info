package com.example.productinfo.controller;

import com.example.productinfo.entity.Promotion;
import com.example.productinfo.repo.PromotionRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PromotionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PromotionRepo promotionRepo;

    ObjectMapper obm  = new ObjectMapper();
    Promotion[] promotion;

    @BeforeEach
    void beforeEach() {
        promotion = new Promotion[3];
        promotion[0] = new Promotion(1L,"2022 쓱데이",1000,0D, Date.valueOf("2022-05-01"),Date.valueOf("2022-07-01"));
        promotion[1] = new Promotion(2L,"스타벅스몰 오픈기념",0,0.05D, Date.valueOf("2021-01-05"),Date.valueOf("2022-12-31"));
        promotion[2] = new Promotion(3L,"2021 쓱데이",2000,0D, Date.valueOf("2021-01-01"),Date.valueOf("2021-01-31"));
    }

    @DisplayName("프로모션 정보 입력 테스트")
    @Test
    void insertPromotionTest() throws Exception {
        for(int i=0;i<promotion.length;i++) {
            String content = obm.writeValueAsString(promotion[i]);

            mockMvc.perform(post("/promotion")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }

    @DisplayName("프로모션 정보 삭제 테스트")
    @Test
    void deletePromotionTest() throws Exception {
        for(int i=0;i<promotion.length;i++) {
            promotionRepo.save(promotion[i]);
        }

        for(Long i=1L;i<=promotion.length;i++) {
            mockMvc.perform(delete("/promotion/{promotionId}", i)
                            .header("promotion-id", i)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }
}