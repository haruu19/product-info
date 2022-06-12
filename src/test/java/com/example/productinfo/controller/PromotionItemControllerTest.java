package com.example.productinfo.controller;

import com.example.productinfo.entity.PromotionItem;
import com.example.productinfo.repo.PromotionItemRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PromotionItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PromotionItemRepo promotionItemRepo;

    ObjectMapper obm  = new ObjectMapper();
    PromotionItem[] pi;

    @BeforeEach
    void beforeEach() {
        pi = new PromotionItem[9];
        pi[0] = new PromotionItem(1L,1L,1L);
        pi[1] = new PromotionItem(2L,1L,2L);
        pi[2] = new PromotionItem(3L,1L,3L);
        pi[3] = new PromotionItem(4L,1L,4L);
        pi[4] = new PromotionItem(5L,1L,5L);
        pi[5] = new PromotionItem(6L,1L,4L);
        pi[6] = new PromotionItem(7L,1L,1L);
        pi[7] = new PromotionItem(8L,1L,2L);
        pi[8] = new PromotionItem(9L,1L,3L);
    }

    @DisplayName("프로모션-아이템 관계 입력 테스트")
    @Test
    void insertPromotionItemTest() throws Exception {
        for(int i=0;i<pi.length;i++) {
            String content = obm.writeValueAsString(pi[i]);

            mockMvc.perform(post("/promotion-item")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }

    @DisplayName("프로모션-아이템 관계 삭제 테스트")
    @Test
    void deletePromotionItemTest() throws Exception {
        for(int i=0;i<pi.length;i++) {
            promotionItemRepo.save(pi[i]);
        }

        for(Long i=1L;i<=pi.length;i++) {
            mockMvc.perform(delete("/promotion-item/{promotionItemId}", i)
                            .header("promotion-item-id", i)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }
}