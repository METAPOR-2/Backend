package com.example.metapor.Domain.user.controller;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.user.dto.*;
import com.example.metapor.Domain.user.service.DoctorService;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.ListResponse;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/clinic-type")
    public ResponseEntity<SimpleResponse> addClinicType(
            @RequestBody ClinicTypeRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
            ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(doctorService.addClinicType(authToken, requestDto));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<RestResponse<GetDoctorInfoResponseDto>> getDoctorInfo(
            @PathVariable("doctorId") Long doctorId,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(doctorService.getDoctorInfo(authToken, doctorId));
    }

    @GetMapping
    public ResponseEntity<ListResponse<DoctorListResponseDto>> getDoctorList(
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(ListResponse.ok(doctorService.getDoctorList(authToken)));
    }

    @GetMapping("/{doctorId}Mypage")
    public ResponseEntity<RestResponse<IntrohospitalRequestDto>> getHospitalInfo(
            @PathVariable("doctorId") Long doctorId,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        // 'Bearer ' 접두어 제거
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;

        // 서비스 호출 후, 응답을 RestResponse 형식으로 반환
        RestResponse<IntrohospitalRequestDto> response = doctorService.getHospitalInfo(authToken, doctorId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{doctorId}/update")
    public ResponseEntity<RestResponse<IntrohospitalRequestDto>> updateHospitalInfo(
            @PathVariable("doctorId") Long doctorId,
            @RequestHeader("Authorization") String authToken,
            @RequestBody IntrohospitalRequestDto updateRequest
    ) throws CustomException {
        RestResponse<IntrohospitalRequestDto> response = doctorService.updateHospitalInfo(authToken, doctorId, updateRequest);
        return ResponseEntity.ok(response);
    }




}
