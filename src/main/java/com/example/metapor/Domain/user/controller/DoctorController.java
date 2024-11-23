package com.example.metapor.Domain.user.controller;


import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.user.dto.*;
import com.example.metapor.Domain.user.service.DoctorService;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.ListResponse;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@Tag(name = "Doctor", description = "의사 관련 API")
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "의사 진료 과목 추가", description = """
            의사 진료 과목을 추가합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
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

    @Operation(summary = "의사 정보 조회", description = """
            의사 정보를 조회합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            의사의 진료 진료 과목들의 ID를 조회 가능합니다(event 생성에 사용)
            """)
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

    @Operation(summary = "의사 전체 목록 조회", description = """
            의사 목록을 조회합니다.<br>
            검색이 가능합니다. 병원 이름이나 의사 이름으로 검색 가능합니다<br>
            별점순으로 정렬됩니다.<br>
            진료 가능 목록 중 하나가 mainClinicType으로 반환됩니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @GetMapping
    public ResponseEntity<ListResponse<DoctorListResponseDto>> getDoctorList(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "number", required = false) Integer number,
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(ListResponse.ok(doctorService.getDoctorList(authToken, query, number, date)));
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
    // test
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
