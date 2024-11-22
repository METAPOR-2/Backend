package com.example.metapor.Domain.user.dto;

import java.util.List;

public record PatientInfoRequestDto(
    String name,
    String phone,
    List<String> needs,
    LocationDto location
) {
}
