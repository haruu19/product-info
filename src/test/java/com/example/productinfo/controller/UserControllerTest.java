package com.example.productinfo.controller;

import com.example.productinfo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.productinfo.repo.UserRepo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepo userRepo;

    ObjectMapper obm  = new ObjectMapper();
    User[] user;

    @BeforeEach
    void beforeEach() {
        user = new User[4];
        user[0] = new User(1L,"이수경","일반","탈퇴");
        user[1] = new User(2L,"최상면","기업회원","정상");
        user[2] = new User(3L,"강재석","일반","정상");
        user[3] = new User(4L,"김구현","일반","정상");
    }

    @DisplayName("사용자 정보 입력 테스트")
    @Test
    void insertUserTest() throws Exception {
        for(int i=0;i<user.length;i++) {
            String content = obm.writeValueAsString(user[i]);

            mockMvc.perform(post("/user")
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }

    @DisplayName("사용자 정보 삭제 테스트")
    @Test
    void deleteUserTest() throws Exception {
        for(int i=0;i<user.length;i++) {
            userRepo.save(user[i]);
        }

        for(Long i=1L;i<=user.length;i++) {
            mockMvc.perform(delete("/user/{userId}", i)
                            .header("user-id", i)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
        }
    }
}