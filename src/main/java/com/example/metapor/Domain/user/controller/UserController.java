package com.example.metapor.Domain.user.controller;

import com.example.metapor.Domain.user.dto.TokenDto;
import com.example.metapor.Domain.user.dto.UserRegisterRequestDto;
import com.example.metapor.Domain.user.service.UserService;
import com.example.metapor.common.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<RestResponse<TokenDto>> registerUser(
            @RequestBody UserRegisterRequestDto userRegisterRequestDto
    ) {
        return ResponseEntity.ok(RestResponse.ok(userService.register(userRegisterRequestDto)));
    }

}
