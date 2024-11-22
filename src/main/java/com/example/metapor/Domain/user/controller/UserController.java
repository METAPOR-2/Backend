package com.example.metapor.Domain.user.controller;

import com.example.metapor.Domain.user.dto.PatientInfoRequestDto;
import com.example.metapor.Domain.user.dto.TokenDto;
import com.example.metapor.Domain.user.dto.UserRegisterRequestDto;
import com.example.metapor.Domain.user.service.UserService;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<RestResponse<TokenDto>> registerUser(
            @RequestBody UserRegisterRequestDto userRegisterRequestDto
    ) throws CustomException {
        return ResponseEntity.ok(RestResponse.ok(userService.register(userRegisterRequestDto)));
    }

    @PostMapping("/user")
    public ResponseEntity<SimpleResponse> loginUser(
            @RequestBody PatientInfoRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
            ) throws CustomException {
        String token = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(userService.addPatientInfo(token, requestDto));
    }
}
