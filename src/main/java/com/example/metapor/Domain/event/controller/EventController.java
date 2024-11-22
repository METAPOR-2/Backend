package com.example.metapor.Domain.event.controller;

import com.example.metapor.Domain.event.dto.CreateEventRequestDto;
import com.example.metapor.Domain.event.service.EventService;
import com.example.metapor.common.dto.JustIdDto;
import com.example.metapor.common.exception.CustomException;
import com.example.metapor.common.response.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/event")
    public ResponseEntity<RestResponse<JustIdDto>> createEvent(
            @RequestBody CreateEventRequestDto requestDto,
            @RequestHeader("Authorization") String authToken
    ) throws CustomException {
        return ResponseEntity.ok(RestResponse.ok(eventService.createEvent(authToken, requestDto)));
    }

}
