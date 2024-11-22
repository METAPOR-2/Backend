package com.example.metapor.Domain.event.dto;

import java.util.List;

public record GetEventPageResponseDto(
        List<EventInfoDto> acceptedEvents,
        List<EventInfoDto> pendingEvents
) {
}
