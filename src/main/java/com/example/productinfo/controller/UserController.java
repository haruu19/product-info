package com.example.productinfo.controller;

import com.example.productinfo.entity.User;
import com.example.productinfo.repo.UserRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepo userRepo;

    @ApiOperation(value = "사용자 입력", notes = "사용자 정보를 입력한다.")
    @PostMapping(value = "/user")
    public User save(@RequestBody User user) {
        return userRepo.save(user);
    }

    @ApiOperation(value = "사용자 삭제", notes = "사용자 정보를 삭제한다.")
    @DeleteMapping(value = "/user/{userId}")
    public void delete(@ApiParam(value = "사용자 아이디", required = true) @RequestHeader(value="user-id") Long userId) {
        userRepo.deleteById(userId);
    }
}