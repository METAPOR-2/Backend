package com.example.metapor.Domain.event.controller;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.event.service.EventService;
import com.example.metapor.common.dto.JustIdDto;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "이벤트 생성", description = """
            이벤트를 생성합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            clinicTypeId는 의사의 정보 조회에서 얻으신 ID를 List형태로 넣으시면 됩니다.
            """)
    @PostMapping("/event")
    public ResponseEntity<RestResponse<JustIdDto>> createEvent(
            @RequestBody CreateEventRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        return ResponseEntity.ok(RestResponse.ok(eventService.createEvent(authToken, requestDto)));
    }

}
