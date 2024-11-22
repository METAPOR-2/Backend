package com.example.metapor.Domain.user.controller;

import com.example.metapor.Domain.user.dto.*;
import com.example.metapor.Domain.user.service.UserService;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = """
            회원가입을 진행합니다.<br>
            의사인지 환자인지를 isDoctor에 넣어주시면 됩니다.
            """)
    @PostMapping("/user/register")
    public ResponseEntity<RestResponse<TokenDto>> registerUser(
            @RequestBody UserRegisterRequestDto userRegisterRequestDto
    ) throws CustomException {
        return ResponseEntity.ok(RestResponse.ok(userService.register(userRegisterRequestDto)));
    }

    @Operation(summary = "환자 정보 입력", description = """
            환자 정보를 입력합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @PostMapping("/user")
    public ResponseEntity<SimpleResponse> loginUser(
            @RequestBody PatientInfoRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
            ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(userService.addPatientInfo(authToken, requestDto));
    }

    @Operation(summary = "의사 정보 입력", description = """
            의사 정보를 입력합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @PostMapping("/doc/user")
    public ResponseEntity<SimpleResponse> loginDoctor(
            @RequestBody DoctorInfoRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        String token = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(userService.addDoctorInfo(token, requestDto));
    }

    @Operation(summary = "내 정보 조회", description = """
            내 정보를 조회합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @GetMapping("/user")
    public ResponseEntity<RestResponse<UserInfoResponseDto>> getMyInfo(
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(RestResponse.ok(userService.getMyInfo(authToken)));
    }
}
