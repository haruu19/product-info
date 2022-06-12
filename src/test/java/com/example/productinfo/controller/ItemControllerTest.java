package com.example.productinfo.controller;

import com.example.productinfo.entity.Promotion;
import com.example.productinfo.entity.PromotionItem;
import com.example.productinfo.entity.Item;
import com.example.productinfo.entity.User;
import com.example.productinfo.repo.ItemRepo;
import com.example.productinfo.repo.PromotionItemRepo;
import com.example.productinfo.repo.PromotionRepo;
import com.example.productinfo.repo.UserRepo;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private PromotionItemRepo promotionItemRepo;

    ObjectMapper obm  = new ObjectMapper();
    Item[] item;
    User[] user;
    Promotion[] promotion;
    PromotionItem[] pi;

    @BeforeEach
    void beforeEach() {
        item = new Item[5];
        item[0] = new Item(1L, "노브랜드 버터링", "일반", 20000, Date.valueOf("2022-01-01"), Date.valueOf("2023-01-01"), 0D);
        item[1] = new Item(2L, "매일 아침 우유", "일반", 1000, Date.valueOf("2021-01-01"), Date.valueOf("2023-05-05"), 0D);
        item[2] = new Item(3L, "나이키 운동화", "기업회원상품", 40000, Date.valueOf("2020-01-01"), Date.valueOf("2023-12-31"), 0D);
        item[3] = new Item(4L, "스타벅스 써머 텀블러", "일반", 15000, Date.valueOf("2021-01-01"), Date.valueOf("2021-08-01"), 0D);
        item[4] = new Item(5L, "크리스마스 케이크", "일반", 30000, Date.valueOf("2022-12-24"), Date.valueOf("2022-12-31"), 0D);

        user = new User[4];
        user[0] = new User(1L,"이수경","일반","탈퇴");
        user[1] = new User(2L,"최상면","기업회원","정상");
        user[2] = new User(3L,"강재석","일반","정상");
        user[3] = new User(4L,"김구현","일반","정상");

        promotion = new Promotion[3];
        promotion[0] = new Promotion(1L,"2022 쓱데이",1000,0D, Date.valueOf("2022-05-01"),Date.valueOf("2022-07-01"));
        promotion[1] = new Promotion(2L,"스타벅스몰 오픈기념",0,0.05D, Date.valueOf("2021-01-05"),Date.valueOf("2022-12-31"));
        promotion[2] = new Promotion(3L,"2021 쓱데이",2000,0D, Date.valueOf("2021-01-01"),Date.valueOf("2021-01-31"));

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

        for(int i=0;i<item.length;i++) itemRepo.save(item[i]);
        for(int i=0;i<user.length;i++) userRepo.save(user[i]);
        for(int i=0;i<promotion.length;i++) promotionRepo.save(promotion[i]);
        for(int i=0;i<pi.length;i++) promotionItemRepo.save(pi[i]);
    }

    @DisplayName("상품 정보 입력 테스트")
    @Test
    void insertItemTest() throws Exception {
        for(int i=0;i<item.length;i++) {
            String content = obm.writeValueAsString(item[i]);

            mockMvc.perform(post("/item")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }

    @DisplayName("상품 정보 삭제 테스트")
    @Test
    void deleteItemTest() throws Exception {
        for(Long i=1L;i<=item.length;i++) {
            mockMvc.perform(delete("/item/{itemId}", i)
                            .header("item-id", i)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }

    @DisplayName("사용자 구매 가능 상품 조회 테스트")
    @Test
    void findItemByUserIdTest() throws Exception {
        for(Long i=1L;i<=user.length;i++) {
            mockMvc.perform(get("/user/{userId}/item", i)
                            .header("user-id", i)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk()).andDo(print());
        }
    }

    @DisplayName("프로모션 포함 상품 조회 테스트")
    @Test
    void findItemWithPromotionByItemIdTest() throws Exception {
        for(Long i=1L;i<=item.length;i++) {
            mockMvc.perform(get("/item/{itemId}/promotion", i)
                            .header("item-id", i)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andDo(print());
        }
    }
}