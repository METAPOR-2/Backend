package com.example.metapor.Domain.user.controller;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.user.dto.ClinicTypeRequestDto;
import com.example.metapor.Domain.user.dto.GetDoctorInfoResponseDto;
import com.example.metapor.Domain.user.service.DoctorService;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
