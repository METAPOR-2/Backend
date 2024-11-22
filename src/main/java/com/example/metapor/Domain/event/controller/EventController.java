package com.example.metapor.Domain.event.controller;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.event.dto.EventInfoDto;
import com.example.metapor.Domain.event.dto.GetEventPageResponseDto;
import com.example.metapor.Domain.event.dto.RejectReasonRequestDto;
import com.example.metapor.Domain.event.service.EventService;
import com.example.metapor.common.dto.JustIdDto;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.ListResponse;
import com.example.metapor.common.response.RestResponse;
import com.example.metapor.common.response.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "Event", description = "이벤트 관련 API")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "이벤트 생성", description = """
            이벤트를 생성합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            clinicTypeId는 의사의 정보 조회에서 얻으신 ID를 List형태로 넣으시면 됩니다.
            """)
    @PostMapping
    public ResponseEntity<RestResponse<JustIdDto>> createEvent(
            @RequestBody CreateEventRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(RestResponse.ok(eventService.createEvent(authToken, requestDto)));
    }

    @Operation(summary = "이벤트 리스트 조회", description = """
            로그인 되어있는 유저의 이벤트 리스트를 조회합니다.<br>
            의사 계정만 조회 가능합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @GetMapping
    public ResponseEntity<RestResponse<GetEventPageResponseDto>> getEventList(
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(RestResponse.ok(eventService.getEventList(authToken)));
    }

    @Operation(summary = "이벤트 상세 조회", description = """
            이벤트 상세 정보를 조회합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @GetMapping("/{eventId}")
    public ResponseEntity<RestResponse<EventInfoDto>> getEvent(
            @PathVariable Long eventId,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(RestResponse.ok(eventService.getEvent(authToken, eventId)));
    }

    @Operation(summary = "이벤트 수락", description = """
            이벤트를 수락합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @PostMapping("/{eventId}/accept")
    public ResponseEntity<SimpleResponse> acceptEvent(
            @PathVariable Long eventId,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(eventService.acceptEvent(authToken, eventId));
    }

    @Operation(summary = "이벤트 거절", description = """
            이벤트를 거절합니다.<br>
            accessToken을 헤더에 넣어주세요.<br>
            """)
    @PostMapping("/{eventId}/reject")
    public ResponseEntity<SimpleResponse> rejectEvent(
            @PathVariable Long eventId,
            @RequestBody RejectReasonRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        authToken = authToken.startsWith("Bearer ") ?
                authToken.substring(7) :
                authToken;
        return ResponseEntity.ok(eventService.rejectEvent(authToken, eventId, requestDto));
    }


}
