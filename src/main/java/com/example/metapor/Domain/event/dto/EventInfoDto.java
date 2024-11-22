package com.example.metapor.Domain.event.dto;

import com.example.metapor.Domain.event.entity.Event;
import com.example.metapor.Domain.user.dto.ClinicTypeResponseDto;

import java.util.List;

public record EventInfoDto(
        Long id,
        String userName,
        int patientNumber,
        String eventDateTime,
        String requestDateTime,
        String address,
        List<ClinicTypeResponseDto> clinicType,
        String comment
) {
    public static EventInfoDto from(Event event) {
        return new EventInfoDto(
                event.getId(),
                event.getPatient().getUser().getName(),
                event.getNumberOfApplicants(),
                event.getApplicationDateTime().toString(),
                event.getCreatedAt().toString(),
                event.getAddress(),
                ClinicTypeResponseDto.from(event.getClinicTypeMappings()),
                event.getComment());
    }

    public static List<EventInfoDto> from(List<Event> events) {
        return events.stream()
                .map(EventInfoDto::from)
                .toList();
    }
}
