package com.example.metapor.Domain.event.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventRequestDto(
        Long doctorId,
        int patientNumber,
        List<Long> clinicTypeIds,
        LocalDateTime dateTime,
        String location,
        String comment
) {
}
