package com.example.metapor.Domain.user.controller;

import com.example.metapor.Domain.user.dto.*;
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

    @PostMapping("/doc/user")
    public ResponseEntity<SimpleResponse> loginDoctor(
            @RequestBody DoctorInfoRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        String token = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(userService.addDocterInfo(token, requestDto));
    }

    @PostMapping("/user/login")
    public ResponseEntity<RestResponse<TokenDto>> generalLogin(
            @RequestBody LoginRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        // Bearer 토큰에서 "Bearer " 접두사를 제거
        String token = authToken.startsWith("Bearer ") ? authToken.substring(7) : authToken;

        return ResponseEntity.ok(RestResponse.ok(userService.Generallogin(token, requestDto)));
    };
}

